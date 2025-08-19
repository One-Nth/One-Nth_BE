package com.onenth.OneNth.domain.product.service.reviewService;

import com.onenth.OneNth.domain.alert.entity.Alert;
import com.onenth.OneNth.domain.alert.entity.FcmToken;
import com.onenth.OneNth.domain.alert.repository.AlertRepository;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.MemberAlertSetting;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.product.converter.ReviewConverter;
import com.onenth.OneNth.domain.product.dto.ReviewRequestDTO;
import com.onenth.OneNth.domain.product.dto.ReviewResponseDTO;
import com.onenth.OneNth.domain.product.entity.*;
import com.onenth.OneNth.domain.product.entity.enums.ItemType;
import com.onenth.OneNth.domain.product.entity.enums.Status;
import com.onenth.OneNth.domain.product.entity.review.*;
import com.onenth.OneNth.domain.product.repository.itemRepository.purchase.PurchaseItemRepository;
import com.onenth.OneNth.domain.product.repository.itemRepository.sharing.SharingItemRepository;
import com.onenth.OneNth.domain.product.repository.reviewRepository.purchase.PurchaseReviewImageRepository;
import com.onenth.OneNth.domain.product.repository.reviewRepository.purchase.PurchaseReviewRepository;
import com.onenth.OneNth.domain.product.repository.reviewRepository.sharing.SharingReviewImageRepository;
import com.onenth.OneNth.domain.product.repository.reviewRepository.sharing.SharingReviewRepository;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.handler.MemberHandler;
import com.onenth.OneNth.global.apiPayload.exception.handler.PurchasingItemHandler;
import com.onenth.OneNth.global.apiPayload.exception.handler.SharingItemHandler;
import com.onenth.OneNth.global.aws.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.onenth.OneNth.domain.alert.converter.AlertConverter.toAlert;
import static com.onenth.OneNth.domain.alert.entity.AlertType.REVIEW;
import static com.onenth.OneNth.domain.alert.fcm.FcmClient.sendNotification;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final MemberRepository memberRepository;

    private final SharingItemRepository sharingItemRepository;
    private final SharingReviewRepository sharingReviewRepository;
    private final SharingReviewImageRepository sharingReviewImageRepository;

    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseReviewRepository purchaseReviewRepository;
    private final PurchaseReviewImageRepository purchaseReviewImageRepository;
    private final AlertRepository alertRepository;

    private final AmazonS3Manager amazonS3Manager;

    // 거래후기 생성
    @Transactional
    @Override
    public ReviewResponseDTO.successCreateSharingReviewDTO createSharingItemReview(
            Long memberId, ReviewRequestDTO.createReview request,
            Long targetSharingItemId, List<MultipartFile> images) {

        Member reviewer = findMemberById(memberId);
        SharingItem item = sharingItemRepository.findById(targetSharingItemId)
                .orElseThrow(() -> new SharingItemHandler(ErrorStatus.SHARING_ITEM_NOT_FOUND));

        if(!item.getStatus().equals(Status.COMPLETED)){
            throw new SharingItemHandler((ErrorStatus.REVIEW_ITEM_NOT_COMPLETED));
        }

        if (item.getMember().getId().equals(reviewer.getId())) {
            throw new SharingItemHandler(ErrorStatus.REVIEW_SELF_PRODUCT_FORBIDDEN);
        }

        SharingReview review = ReviewConverter.toSharingReview(request, reviewer, item);
        sharingReviewRepository.save(review);

        saveReviewImages(images, review, ReviewType.SHARING);

        Member targetUser = item.getMember();
        if(targetUser.getMemberAlertSetting().isReviewAlerts()){
            String title = "새로운 거래 후기가 도착했어요 \uD83D\uDED2";
            String body = reviewer.getName() + "님이 보낸 \"" + item.getTitle() + "\" 거래 후기가 도착했습니다.";
            
            targetUser.getFcmTokens().forEach(token -> 
                sendNotification(token.getFcmToken(), title, body)
            );
            alertRepository.save(toAlert(targetUser, REVIEW, ItemType.SHARE, review.getId(), body, title));
        }
        return new ReviewResponseDTO.successCreateSharingReviewDTO(review.getId());
    }

    @Transactional
    @Override
    public ReviewResponseDTO.successCreatePurchaseReviewDTO createPurchaseItemReview(
            Long memberId, ReviewRequestDTO.createReview request,
            Long targetPurchaseItemId, List<MultipartFile> images) {

        Member reviewer = findMemberById(memberId);
        PurchaseItem item = purchaseItemRepository.findById(targetPurchaseItemId)
                .orElseThrow(() -> new SharingItemHandler(ErrorStatus.PURCHASE_ITEM_NOT_FOUND));

        PurchaseReview review = ReviewConverter.toPurchaseReview(request, reviewer, item);
        purchaseReviewRepository.save(review);

        saveReviewImages(images, review, ReviewType.PURCHASE);

        return new ReviewResponseDTO.successCreatePurchaseReviewDTO(review.getId());
    }

    @Transactional
    @Override
    public void updateReview(ReviewRequestDTO.createReview request, ItemType itemType, Long reviewId, Long memberId) {
        Member member = findMemberById(memberId);

        Review review = switch (itemType) {
            case SHARE -> sharingReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new SharingItemHandler(ErrorStatus.REVIEW_NOT_FOUND));
            case PURCHASE -> purchaseReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new PurchasingItemHandler(ErrorStatus.REVIEW_NOT_FOUND));
        };

        if(!review.getMember().equals(member)) {
            throw new MemberHandler(ErrorStatus._FORBIDDEN);
        }

        review.setContent(request.getContent());
        review.setRate(request.getRate());
    }

    @Transactional
    @Override
    public void deleteReviewImage(ReviewRequestDTO.DeleteReviewImages request, ItemType itemType, Long reviewId, Long memberId) {
        Member member = findMemberById(memberId);

        Review review = switch (itemType) {
            case SHARE -> sharingReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new SharingItemHandler(ErrorStatus.REVIEW_NOT_FOUND));
            case PURCHASE -> purchaseReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new PurchasingItemHandler(ErrorStatus.REVIEW_NOT_FOUND));
        };

        if (!review.getMember().equals(member)) {
            throw new MemberHandler(ErrorStatus._FORBIDDEN);
        }

        List<Long> deleteImageList = request.getImageIds();
        if (deleteImageList.isEmpty()) {
            return;
        }

        List<ReviewImage> imagesToRemove = review.getReviewImages().stream()
                .filter(img -> deleteImageList.contains(img.getId()))
                .collect(Collectors.toList());

        imagesToRemove.forEach(img -> amazonS3Manager.deleteFile(img.getImageUrl()));
        review.getReviewImages().removeAll(imagesToRemove);
    }

    @Transactional
    @Override
    public void uploadNewReviewImage(List<MultipartFile> images, ItemType itemType, Long reviewId, Long memberId) {
        Member member = findMemberById(memberId);

        Review review = switch (itemType) {
            case SHARE -> sharingReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new SharingItemHandler(ErrorStatus.REVIEW_NOT_FOUND));
            case PURCHASE -> purchaseReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new PurchasingItemHandler(ErrorStatus.REVIEW_NOT_FOUND));
        };

        if (!review.getMember().equals(member)) {
            throw new MemberHandler(ErrorStatus._FORBIDDEN);
        }

        if(itemType.equals(ItemType.SHARE)){
            saveReviewImages(images, review, ReviewType.SHARING);
        }else{
            saveReviewImages(images, review, ReviewType.PURCHASE);
        }
    }

    private void saveReviewImages(List<MultipartFile> images, Object review, ReviewType type) {
        if (images == null || images.isEmpty()) return;

        for (MultipartFile image : images) {
            String uuid = UUID.randomUUID().toString();
            String keyName = amazonS3Manager.generateReviewKeyName(uuid);
            String imageUrl = amazonS3Manager.uploadFile(keyName, image);

            if (type == ReviewType.SHARING && review instanceof SharingReview sharingReview) {
                SharingReviewImage reviewImage = SharingReviewImage.builder()
                        .imageUrl(imageUrl)
                        .sharingReview(sharingReview)
                        .build();

                sharingReviewImageRepository.save(reviewImage);
                sharingReview.addReviewImage(reviewImage);
            }

            if (type == ReviewType.PURCHASE && review instanceof PurchaseReview purchaseReview) {
                PurchaseReviewImage reviewImage = PurchaseReviewImage.builder()
                        .imageUrl(imageUrl)
                        .purchaseReview(purchaseReview)
                        .build();

                purchaseReviewImageRepository.save(reviewImage);
                purchaseReview.addReviewImage(reviewImage);
            }
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private enum ReviewType {
        SHARING, PURCHASE
    }
}