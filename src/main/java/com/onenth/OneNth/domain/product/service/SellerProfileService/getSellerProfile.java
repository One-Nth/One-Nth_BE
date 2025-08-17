package com.onenth.OneNth.domain.product.service.SellerProfileService;


import com.amazonaws.services.kms.model.NotFoundException;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.MemberRegion;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRegionRepository;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.product.dto.ReviewResponseDTO;
import com.onenth.OneNth.domain.product.dto.SellerProfileResponseDTO;
import com.onenth.OneNth.domain.product.entity.PurchaseItem;
import com.onenth.OneNth.domain.product.entity.SharingItem;
import com.onenth.OneNth.domain.product.entity.enums.ItemType;
import com.onenth.OneNth.domain.product.entity.enums.Status;
import com.onenth.OneNth.domain.product.entity.review.PurchaseReview;
import com.onenth.OneNth.domain.product.entity.review.PurchaseReviewImage;
import com.onenth.OneNth.domain.product.entity.review.SharingReview;
import com.onenth.OneNth.domain.product.entity.review.SharingReviewImage;
import com.onenth.OneNth.domain.product.repository.itemRepository.purchase.PurchaseItemRepository;
import com.onenth.OneNth.domain.product.repository.itemRepository.sharing.SharingItemRepository;
import com.onenth.OneNth.domain.product.repository.reviewRepository.purchase.PurchaseReviewRepository;
import com.onenth.OneNth.domain.product.repository.reviewRepository.sharing.SharingReviewRepository;
import com.onenth.OneNth.domain.region.entity.Region;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class getSellerProfile {

    private final MemberRepository memberRepository;
    private final MemberRegionRepository memberRegionRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseReviewRepository purchaseReviewRepository;
    private final SharingItemRepository sharingItemRepository;
    private final SharingReviewRepository sharingReviewRepository;
    public SellerProfileResponseDTO getPurchaseSellerProfile(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        MemberRegion memberRegion = memberRegionRepository.findByMemberId(member.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("대표 지역이 없습니다."));

        Region region = memberRegion.getRegion();

        List<PurchaseItem> items = purchaseItemRepository.findByMember(member);

        List<SellerProfileResponseDTO.PurchaseItemSummaryDTO> itemDTOs = items.stream()
                .map(item -> SellerProfileResponseDTO.PurchaseItemSummaryDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .status(item.getStatus())
                        .itemCategory(item.getItemCategory())
                        .purchaseMethod(item.getPurchaseMethod())
                        .thumbnailUrl(item.getItemImages().isEmpty() ? null : item.getItemImages().get(0).getUrl())
                        .build())
                .toList();

        List<PurchaseReview> receivedReviews = items.stream()
                .flatMap(i -> i.getPurchaseReviews().stream())
                .toList();

        int reviewCount = receivedReviews.size();
        double avgRating = receivedReviews.stream()
                .map(PurchaseReview::getRate)
                .filter(Objects::nonNull)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0.0);

        List<ReviewResponseDTO.getReviewDTO> reviewDTOs = receivedReviews.stream()
                .sorted(java.util.Comparator.comparing(PurchaseReview::getCreatedAt).reversed())
                .limit(3)
                .map(r -> ReviewResponseDTO.getReviewDTO.builder()
                        .reviewId(r.getId())
                        .reviewerId(r.getMember().getId()) // 작성자(구매자)
                        .itemId(r.getPurchaseItem().getId())
                        .itemType(ItemType.PURCHASE)
                        .createdAt(r.getCreatedAt())
                        .content(r.getContent())
                        .rate(r.getRate())
                        .reviewImageList(
                                r.getReviewImages().stream()
                                        .map(img -> ((PurchaseReviewImage) img).getImageUrl())
                                        .toList()
                        )
                        .build())
                .toList();

        return SellerProfileResponseDTO.builder()
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .isVerified(member.isVerified())
                .mainRegionName(region.getRegionName())
                .totalSalesCount(items.size())
                .totalReviewCount(reviewCount)
                .averageRating(avgRating)
                .items(itemDTOs)
                .recentReviews(reviewDTOs)
                .build();
    }

    public SellerProfileResponseDTO getSharingSellerProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("사용자 없음"));

        Region region = memberRegionRepository.findByMemberId(memberId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("대표 지역 없음"))
                .getRegion();

        List<SharingItem> items = sharingItemRepository.findByMember(member);

        List<SellerProfileResponseDTO.PurchaseItemSummaryDTO> itemDTOs = items.stream()
                .map(item -> SellerProfileResponseDTO.PurchaseItemSummaryDTO.builder()
                        .id(item.getId())
                        .name(item.getTitle())
                        .status(item.getStatus())
                        .price(item.getPrice() == null ? 0 : item.getPrice())
                        .itemCategory(item.getItemCategory())
                        .purchaseMethod(item.getPurchaseMethod())
                        .thumbnailUrl(item.getItemImages().isEmpty() ? null : item.getItemImages().get(0).getUrl())
                        .build())
                .toList();

        List<SharingReview> receivedReviews = items.stream()
                .flatMap(i -> i.getSharingReviews().stream())
                .toList();

        int reviewCount = receivedReviews.size();

        double avgRating = receivedReviews.stream()
                .map(SharingReview::getRate)
                .filter(Objects::nonNull)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0.0);

        List<ReviewResponseDTO.getReviewDTO> reviewDTOs = receivedReviews.stream()
                .sorted(java.util.Comparator
                        .comparing(SharingReview::getCreatedAt).reversed()
                        .thenComparing(SharingReview::getId, java.util.Comparator.nullsLast(java.util.Comparator.reverseOrder())))
                .limit(3)
                .map(r -> ReviewResponseDTO.getReviewDTO.builder()
                        .reviewId(r.getId())
                        .reviewerId(r.getMember().getId())
                        .itemId(r.getSharingItem().getId())
                        .itemType(ItemType.SHARE)
                        .createdAt(r.getCreatedAt())
                        .content(r.getContent())
                        .rate(r.getRate())
                        .reviewImageList(
                                r.getReviewImages().stream()
                                        .map(img -> ((SharingReviewImage) img).getImageUrl())
                                        .toList()
                        )
                        .build())
                .toList();

        return SellerProfileResponseDTO.builder()
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .isVerified(member.isVerified())
                .mainRegionName(region.getRegionName())
                .totalSalesCount(items.size())
                .totalReviewCount(reviewCount)
                .averageRating(avgRating)
                .items(itemDTOs)
                .recentReviews(reviewDTOs)
                .build();
    }

    public SellerProfileResponseDTO.TradeHistoryResponseDTO countUserReceivedReviews(Long userId) {
        Member targetMember = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<SharingItem> sharingItems = sharingItemRepository.findByMember(targetMember);
        List<PurchaseItem> purchaseItems = purchaseItemRepository.findByMember(targetMember);

        int totalReviewCount = calculateTotalReviewCount(sharingItems, purchaseItems);
        BigDecimal totalRatingSum = calculateTotalRatingSum(sharingItems, purchaseItems);
        double averageRating = calculateAverageRating(totalRatingSum, totalReviewCount);
        int totalDealsCount = calculateTotalDealsCount(sharingItems, purchaseItems);

        return SellerProfileResponseDTO.TradeHistoryResponseDTO.builder()
                .userId(targetMember.getId())
                .reviewCount(totalReviewCount)
                .totalRating(averageRating)
                .totalDealsCount(totalDealsCount)
                .build();
    }

    private int calculateTotalReviewCount(List<SharingItem> sharingItems, List<PurchaseItem> purchaseItems) {
        return sharingItems.stream()
                .mapToInt(item -> item.getSharingReviews().size())
                .sum()
                + purchaseItems.stream()
                .mapToInt(item -> item.getPurchaseReviews().size())
                .sum();
    }

    private BigDecimal calculateTotalRatingSum(List<SharingItem> sharingItems, List<PurchaseItem> purchaseItems) {
        BigDecimal sharingSum = sharingItems.stream()
                .flatMap(item -> item.getSharingReviews().stream())
                .map(SharingReview::getRate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal purchaseSum = purchaseItems.stream()
                .flatMap(item -> item.getPurchaseReviews().stream())
                .map(PurchaseReview::getRate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sharingSum.add(purchaseSum);
    }

    private double calculateAverageRating(BigDecimal totalRatingSum, int totalReviewCount) {
        if (totalReviewCount == 0) return 0.0;
        return totalRatingSum.divide(BigDecimal.valueOf(totalReviewCount), 1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private int calculateTotalDealsCount(List<SharingItem> sharingItems, List<PurchaseItem> purchaseItems) {
        long sharingCount = sharingItems.stream()
                .filter(item -> item.getStatus() == Status.COMPLETED)
                .count();
        long purchaseCount = purchaseItems.stream()
                .filter(item -> item.getStatus() == Status.COMPLETED)
                .count();
        return (int) (sharingCount + purchaseCount);
    }

}