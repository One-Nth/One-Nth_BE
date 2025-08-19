package com.onenth.OneNth.domain.member.settings.alert.keywordAlert.util;

import com.onenth.OneNth.domain.alert.entity.FcmToken;
import com.onenth.OneNth.domain.alert.repository.AlertRepository;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.ProductKeywordAlert;
import com.onenth.OneNth.domain.member.entity.RegionKeywordAlert;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.ProductKeywordAlertRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.RegionKeywordAlertRepository;
import com.onenth.OneNth.domain.product.entity.enums.ItemType;
import com.onenth.OneNth.domain.region.entity.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.onenth.OneNth.domain.alert.converter.AlertConverter.toAlert;
import static com.onenth.OneNth.domain.alert.entity.AlertType.ITEM;
import static com.onenth.OneNth.domain.alert.fcm.FcmClient.sendNotification;

@Component
@RequiredArgsConstructor
public class KeywordAlertUtil {

    private final RegionKeywordAlertRepository regionKeywordAlertRepository;
    private final ProductKeywordAlertRepository productKeywordAlertRepository;
    private final AlertRepository alertRepository;

    public static List<Object> mergeAndSortAlerts(List<ProductKeywordAlert> productKeywordAlertList, List<RegionKeywordAlert> regionKeywordAlertList) {
        List<Object> mergedAlerts = new ArrayList<>();
        mergedAlerts.addAll(productKeywordAlertList);
        mergedAlerts.addAll(regionKeywordAlertList);

        mergedAlerts.sort(Comparator.comparing(alert ->
                        (alert instanceof ProductKeywordAlert)
                                ? ((ProductKeywordAlert) alert).getCreatedAt()
                                : ((RegionKeywordAlert) alert).getCreatedAt(),
                Comparator.reverseOrder())
        );

        return mergedAlerts;
    }

    // 지역 키워드 알림 전송
    public void getRegionKeywordAlertMemberTokens(Region region, String productName, Long id, ItemType itemType, Member registerMember){
        List<FcmToken> tokens = regionKeywordAlertRepository.findByRegionKeywordAndEnabled(region, true)
                .stream()
                .filter(alert -> alert.getMember() != null)
                .filter(alert -> !alert.getMember().equals(registerMember))
                .flatMap(alert -> alert.getMember().getFcmTokens().stream())
                .toList();

        if (!tokens.isEmpty()) {
            String title = "\"" + region.getRegionName() + "\" 지역 알림 \uD83D\uDD14";
            String serviceText = itemType == ItemType.PURCHASE ? "같이사요" : "함께나눠요";
            String body = "\"" + productName + "\"가 " + serviceText + "에 등록되었습니다.";

            tokens.forEach(token ->
                    sendNotification(token.getFcmToken(), title, body)
            );

            tokens.stream()
                    .map(FcmToken::getMember)
                    .distinct()
                    .forEach(targetUser ->
                            alertRepository.save(toAlert(targetUser, ITEM, itemType, id, body, title))
                    );
        }
    }

    // 상품명 키워드 알림 전송
    public void getProductKeywordAlertMemberTokens(String productName, Member registerMember, Long id, ItemType itemType){
        String[] keywords = productName.split("\\s+");

        Arrays.stream(keywords)
                .forEach(keyword -> {
                    List<FcmToken> tokens = productKeywordAlertRepository.findByKeywordAndEnabled(keyword, true)
                            .stream()
                            .filter(alert -> alert.getMember() != null)
                            .filter(alert -> !alert.getMember().equals(registerMember))
                            .flatMap(alert -> alert.getMember().getFcmTokens().stream())
                            .distinct()
                            .toList();

                    if (!tokens.isEmpty()) {
                        String title = "\"" + keyword + "\" 상품 키워드 알림 \uD83D\uDD14";
                        String serviceText = itemType == ItemType.PURCHASE ? "같이사요" : "함께나눠요";
                        String body = "\"" + productName + "\"가 " + serviceText + "에 등록되었습니다.";

                        tokens.forEach(token ->
                                sendNotification(token.getFcmToken(), title, body)
                        );

                        tokens.stream()
                                .map(FcmToken::getMember)
                                .distinct()
                                .forEach(targetUser ->
                                        alertRepository.save(toAlert(targetUser, ITEM, itemType, id, body, title))
                                );
                    }
                });
    }
}