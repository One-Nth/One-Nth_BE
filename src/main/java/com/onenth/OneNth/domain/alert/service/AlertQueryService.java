package com.onenth.OneNth.domain.alert.service;

import com.onenth.OneNth.domain.alert.dto.AlertResponseDTO;
import com.onenth.OneNth.domain.alert.entity.Alert;
import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.alert.repository.AlertRepository;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertQueryService {

    private final MemberRepository memberRepository;
    private final AlertRepository alertRepository;

    // N분의 1 알림 로그 조회
    public List<AlertResponseDTO.DealNotificationResponseDTO> getDealNotificationLogs(Long memberId) {
        List<AlertType> alertTypeList = Arrays.asList(AlertType.ITEM, AlertType.REVIEW);
        List<Alert> alertList = findRecentAlerts(memberId, alertTypeList);

        List<AlertResponseDTO.DealNotificationResponseDTO> dtoList = alertList.stream()
                .map(alert -> AlertResponseDTO.DealNotificationResponseDTO.builder()
                        .alertType(alert.getAlertType())
                        .itemType(alert.getItemType())
                        .contentId(alert.getContentId())
                        .message(alert.getMessage())
                        .readStatus(alert.isRead())
                        .build())
                .collect(Collectors.toList());

        // 읽음 상태가 false인 알림만 읽음 처리
        alertList.stream()
                .filter(alert -> !alert.isRead())
                .forEach(alert -> alert.setRead(true));

        return dtoList;
    }

    // 꿀팁 N분의 1 알림 로그 조회
    public List<AlertResponseDTO.PostNotificationResponseDTO> getPostNotificationLogs(Long memberId) {
        List<AlertType> alertTypeList = Arrays.asList(AlertType.DISCOUNT, AlertType.RESTAURANT, AlertType.LIFE_TIP);
        List<Alert> alertList = findRecentAlerts(memberId, alertTypeList);

        List<AlertResponseDTO.PostNotificationResponseDTO> dtoList = alertList.stream()
                .map(alert -> AlertResponseDTO.PostNotificationResponseDTO.builder()
                        .alertType(alert.getAlertType())
                        .contentId(alert.getContentId())
                        .message(alert.getMessage())
                        .readStatus(alert.isRead())
                        .build())
                .collect(Collectors.toList());

        alertList.stream()
                .filter(alert -> !alert.isRead())
                .forEach(alert -> alert.setRead(true));

        return dtoList;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public List<Alert> findRecentAlerts(Long memberId, List<AlertType> alertTypes) {
        Member member = findMemberById(memberId);
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        return alertRepository.findByMemberAndAlertTypeInAndCreatedAtAfter(member, alertTypes, oneWeekAgo);
    }
}