package com.onenth.OneNth.domain.member.settings.alert.keywordAlert.service;

import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.converter.KeywordAlertConverter;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.dto.KeywordAlertRequestDTO;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.dto.KeywordAlertResponseDTO;
import com.onenth.OneNth.domain.member.entity.ProductKeywordAlert;
import com.onenth.OneNth.domain.member.entity.RegionKeywordAlert;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.ProductKeywordAlertRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.RegionKeywordAlertRepository;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.util.KeywordAlertUtil;
import com.onenth.OneNth.domain.region.entity.Region;
import com.onenth.OneNth.domain.region.repository.RegionRepository;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordAlertCommandServiceImpl implements KeywordAlertCommandService {

    private final RegionKeywordAlertRepository regionKeywordAlertRepository;
    private final ProductKeywordAlertRepository productKeywordAlertRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;

    @Override
    public KeywordAlertResponseDTO.AddKeywordAlertResponseDTO addRegionKeyword(Long userId, Long regionId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.REGION_NOT_FOUND));

        if (regionKeywordAlertRepository.countByMember(member) >= 3) {
            throw new GeneralException(ErrorStatus.REGION_KEYWORD_LIMIT_EXCEEDED);
        }

        if (regionKeywordAlertRepository.existsByMemberAndRegionKeyword(member, region)) {
            throw new GeneralException(ErrorStatus.REGION_KEYWORD_ALREADY_EXISTS);
        }

        RegionKeywordAlert regionKeywordAlert = KeywordAlertConverter.toRegionKeywordAlert(member, region);

        return KeywordAlertConverter.toAddKeywordAlertResponseDTO(regionKeywordAlertRepository.save(regionKeywordAlert));

    }

    @Override
    public KeywordAlertResponseDTO.SetKeywordAlertStatusResponseDTO setRegionAlertStatus(
            Long userId,
            Long regionKeywordAlertId,
            KeywordAlertRequestDTO.SetRegionAlertStatusRequestDTO request) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        RegionKeywordAlert regionKeywordAlert = regionKeywordAlertRepository.findByIdAndMember(regionKeywordAlertId, member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.REGION_KEYWORD_NOT_FOUND_OR_NOT_YOURS));

        if (request.getIsEnabled()) {
            regionKeywordAlert.enable();
        } else {
            regionKeywordAlert.disable();
        }

        return KeywordAlertConverter.toSetKeywordAlertStatusResponseDTO(regionKeywordAlertRepository.save(regionKeywordAlert));
    }

    @Override
    public KeywordAlertResponseDTO.AddKeywordAlertResponseDTO addProductKeyword(Long userId, KeywordAlertRequestDTO.AddKeywordAlertRequestDTO request) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        if (productKeywordAlertRepository.countByMember(member) >= 5) {
            throw new GeneralException(ErrorStatus.PRODUCT_KEYWORD_LIMIT_EXCEEDED);
        }

        if (productKeywordAlertRepository.existsByMemberAndKeyword(member, request.getKeyword())) {
            throw new GeneralException(ErrorStatus.PRODUCT_KEYWORD_ALREADY_EXISTS);
        }

        ProductKeywordAlert productKeywordAlert = KeywordAlertConverter.toProductKeywordAlert(member, request.getKeyword());

        return KeywordAlertConverter.toAddKeywordAlertResponseDTO(productKeywordAlertRepository.save(productKeywordAlert));

    }

    @Override
    public KeywordAlertResponseDTO.SetKeywordAlertStatusResponseDTO setProductKeywordAlertStatus(
            Long userId,
            Long productKeywordAlertId,
            KeywordAlertRequestDTO.SetKeywordAlertStatusRequestDTO request
    ) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        ProductKeywordAlert productKeywordAlert = productKeywordAlertRepository.findByIdAndMember(productKeywordAlertId, member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PRODUCT_KEYWORD_NOT_FOUND_OR_NOT_YOURS));

        if (request.getIsEnabled()) {
            productKeywordAlert.enable();
        } else {
            productKeywordAlert.disable();
        }

        return KeywordAlertConverter.toSetKeywordAlertStatusResponseDTO(productKeywordAlertRepository.save(productKeywordAlert));
    }

    @Override
    public KeywordAlertResponseDTO.AlertListResponseDTO updateKeywordAlertList(
            Long userId,
            KeywordAlertRequestDTO.UpdateKeywordAlertListRequestDTO request
    ) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        List<Long> productKeywordIds = request.getProductKeywordIdList();
        List<Long> regionKeywordIds = request.getRegionKeywordIdList();

        deleteUnselectedKeywordAlerts(member, productKeywordIds, regionKeywordIds);

        List<ProductKeywordAlert> productKeywordAlertList = findProductAlertsByIds(productKeywordIds, member);
        List<RegionKeywordAlert> regionKeywordAlertList = findRegionAlertsByIds(regionKeywordIds, member);

        List<Object> mergedAlerts = KeywordAlertUtil.mergeAndSortAlerts(productKeywordAlertList, regionKeywordAlertList);

        List<KeywordAlertResponseDTO.AlertSummary> alertSummaryList = mergedAlerts.stream()
                .map(alert -> KeywordAlertConverter.toAlertSummary(alert))
                .collect(Collectors.toList());

        return KeywordAlertConverter.toAlertListResponseDTO(alertSummaryList);
    }

    public void deleteUnselectedKeywordAlerts(Member member, List<Long> productAlertIds, List<Long> regionAlertIds) {
        List<ProductKeywordAlert> existingProductKeywordAlerts = productKeywordAlertRepository.findAllByMember(member);
        List<RegionKeywordAlert> existingRegionAlerts = regionKeywordAlertRepository.findAllByMember(member);

        List<ProductKeywordAlert> productKeywordAlertsToDelete = existingProductKeywordAlerts.stream()
                .filter(alert -> !productAlertIds.contains(alert.getId()))
                .collect(Collectors.toList());

        List<RegionKeywordAlert> regionAlertsToDelete = existingRegionAlerts.stream()
                .filter(alert -> !regionAlertIds.contains(alert.getId()))
                .collect(Collectors.toList());

        productKeywordAlertRepository.deleteAll(productKeywordAlertsToDelete);
        regionKeywordAlertRepository.deleteAll(regionAlertsToDelete);
    }

    private List<ProductKeywordAlert> findProductAlertsByIds(List<Long> productAlertIds, Member member) {
        return productAlertIds.stream().map(
                id -> {
                    return productKeywordAlertRepository.findByIdAndMember(id, member)
                            .orElseThrow(() -> new GeneralException(ErrorStatus.PRODUCT_KEYWORD_NOT_FOUND_OR_NOT_YOURS));
                }).collect(Collectors.toList());
    }

    private List<RegionKeywordAlert> findRegionAlertsByIds(List<Long> regionAlertIds, Member member) {
        return regionAlertIds.stream().map(
                id -> {
                    return regionKeywordAlertRepository.findByIdAndMember(id, member)
                            .orElseThrow(() -> new GeneralException(ErrorStatus.REGION_KEYWORD_NOT_FOUND_OR_NOT_YOURS));
                }).collect(Collectors.toList());
    }
}
