package com.onenth.OneNth.domain.member.settings.alert.generalAlert.service;

import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.member.entity.*;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.converter.GeneralAlertConverter;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertRequestDTO;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertResponseDTO;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.repository.MemberAlertSettingRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.ProductKeywordAlertRepository;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository.RegionKeywordAlertRepository;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GeneralAlertCommandServiceImpl implements GeneralAlertCommandService {

    private final MemberRepository memberRepository;
    private final MemberAlertSettingRepository memberAlertSettingRepository;
    private final ProductKeywordAlertRepository productKeywordAlertRepository;
    private final RegionKeywordAlertRepository regionKeywordAlertRepository;

/*    @Override
    public GeneralAlertResponseDTO.SetScrapAlertStatusResponseDTO setScrapAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetScrapAlertStatusRequestDTO request
    ) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        MemberAlertSetting memberAlertSetting = memberAlertSettingRepository.findByMember(member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALERT_SETTING_NOT_FOUND));

        if (request.getIsEnabled()) {
            memberAlertSetting.enableAlerts(AlertType.SCRAP);
        } else {
            memberAlertSetting.disableAlerts(AlertType.SCRAP);
        }

        return GeneralAlertConverter.toSetScrapAlertStatusResponseDTO(memberAlertSetting);
    }*/

    @Override
    public GeneralAlertResponseDTO.SetReviewAlertStatusResponseDTO setReviewAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetReviewAlertStatusRequestDTO request
    ) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        MemberAlertSetting memberAlertSetting = memberAlertSettingRepository.findByMember(member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALERT_SETTING_NOT_FOUND));

        if (request.getIsEnabled()) {
            memberAlertSetting.enableAlerts(AlertType.REVIEW);
        } else {
            memberAlertSetting.disableAlerts(AlertType.REVIEW);
        }

        return GeneralAlertConverter.toSetReviewAlertStatusResponseDTO(memberAlertSetting);
    }

    @Override
    public GeneralAlertResponseDTO.SetChatAlertStatusResponseDTO setChatAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetChatAlertStatusRequestDTO request
    ) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        MemberAlertSetting memberAlertSetting = memberAlertSettingRepository.findByMember(member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALERT_SETTING_NOT_FOUND));

        if (request.getIsEnabled()) {
            memberAlertSetting.enableAlerts(AlertType.CHAT);
        } else {
            memberAlertSetting.disableAlerts(AlertType.CHAT);
        }

        return GeneralAlertConverter.toSetChatAlertStatusResponseDTO(memberAlertSetting);
    }

    @Override
    public GeneralAlertResponseDTO.SetCommentAlertStatusResponseDTO setCommentAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetCommentAlertStatusRequestDTO request
    ) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        MemberAlertSetting memberAlertSetting = memberAlertSettingRepository.findByMember(member)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALERT_SETTING_NOT_FOUND));

        if (request.getIsEnabled()) {
            memberAlertSetting.enableAlerts(AlertType.COMMENT);
        } else {
            memberAlertSetting.disableAlerts(AlertType.COMMENT);
        }

        return GeneralAlertConverter.toSetCommentAlertStatusResponseDTO(memberAlertSetting);
    }
}
