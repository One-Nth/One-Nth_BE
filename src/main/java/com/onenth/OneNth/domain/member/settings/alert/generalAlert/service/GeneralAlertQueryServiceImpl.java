package com.onenth.OneNth.domain.member.settings.alert.generalAlert.service;

import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.MemberAlertSetting;
import com.onenth.OneNth.domain.member.entity.ProductKeywordAlert;
import com.onenth.OneNth.domain.member.entity.RegionKeywordAlert;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.converter.GeneralAlertConverter;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertResponseDTO;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.repository.MemberAlertSettingRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.ProductKeywordAlertRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.RegionKeywordAlertRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.util.KeywordAlertUtil;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GeneralAlertQueryServiceImpl implements GeneralAlertQueryService {

    private final MemberRepository memberRepository;
    private final MemberAlertSettingRepository memberAlertSettingRepository;
    private final ProductKeywordAlertRepository productKeywordAlertRepository;
    private final RegionKeywordAlertRepository regionKeywordAlertRepository;

    @Override
    public GeneralAlertResponseDTO.GetAllAlertSettingsResponseDTO getAllAlertSettings(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        MemberAlertSetting memberAlertSetting = memberAlertSettingRepository.findByMember(member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALERT_SETTING_NOT_FOUND));

        List<ProductKeywordAlert> productKeywordAlerts = productKeywordAlertRepository.findAllByMember(member);
        List<RegionKeywordAlert> regionKeywordAlerts = regionKeywordAlertRepository.findAllByMember(member);

        GeneralAlertResponseDTO.GeneralAlertSummary reviewAlertSummary = GeneralAlertConverter.toGeneralAlertSummary(AlertType.REVIEW, memberAlertSetting);
        GeneralAlertResponseDTO.GeneralAlertSummary commentAlertSummary = GeneralAlertConverter.toGeneralAlertSummary(AlertType.COMMENT, memberAlertSetting);

        List<Object> mergedAlerts = KeywordAlertUtil.mergeAndSortAlerts(productKeywordAlerts, regionKeywordAlerts);

        List<GeneralAlertResponseDTO.KeywordAlertSummary> keywordAlertSummaryList = mergedAlerts.stream()
                .map(keywordAlert -> GeneralAlertConverter.toKeywordAlertSummary(keywordAlert))
                .collect(Collectors.toList());

        return GeneralAlertConverter.toGetAllAlertSettingsResponseDTO(reviewAlertSummary, commentAlertSummary, keywordAlertSummaryList);
    }
}
