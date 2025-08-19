package com.onenth.OneNth.domain.alert.controller;

import com.onenth.OneNth.domain.alert.dto.AlertResponseDTO;
import com.onenth.OneNth.domain.alert.service.AlertQueryService;
import com.onenth.OneNth.global.apiPayload.ApiResponse;
import com.onenth.OneNth.global.auth.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alert")
@RequiredArgsConstructor
@Tag(name = "알림 관련 API")
public class AlertController {

    private final AlertQueryService alertQueryService;

    @Operation(
            summary = "N분의 1 알림 로그 조회 API",
            description = """
                    현재 사용자의 \"N분의 1\" 알림 로그를 조회하는 API입니다.
                    - 해당 호출 이후, 읽지않음 상태의 알림들은 읽음상태로 전환 됩니다.
                    - 기본으로 최신순 조회되며, 호출 시점으로부터 7일 동안의 알림 로그들을 조회합니다.
                    - 같이 사요, 함께 나눠요 관련 알림(거래후기 : REVIEW, 상품등록 : ITEM)을 조회합니다."""
    )
    @GetMapping("/deal")
    public ApiResponse<List<AlertResponseDTO.DealNotificationResponseDTO>> getDealNotificationLogs(
            @AuthUser Long memberId
    ) {
        List<AlertResponseDTO.DealNotificationResponseDTO> logs
                = alertQueryService.getDealNotificationLogs(memberId);
        return ApiResponse.onSuccess(logs);
    }

    @Operation(
            summary = "꿀팁 N분의 1 알림 로그 조회 API",
            description = """
                    현재 사용자의 \"꿀팁 N분의 1\" 알림 로그를 조회하는 API입니다.
                    - 해당 호출 이후, 읽지않음 상태의 알림들은 읽음상태로 전환 됩니다.
                    - 기본으로 최신순 조회되며, 호출 시점으로부터 7일 동안의 알림 로그들을 조회합니다.
                    - 생활 정보(LIFE_TIP), 할인 정보(DISCOUNT), 우리동네 맛집/카페(RESTAURANT) 관련 알림을 조회합니다."""
    )
    @GetMapping("/post")
    public ApiResponse<List<AlertResponseDTO.PostNotificationResponseDTO>> getPostNotificationLogs(
            @AuthUser Long memberId
    ) {
        List<AlertResponseDTO.PostNotificationResponseDTO> logs
                = alertQueryService.getPostNotificationLogs(memberId);
        return ApiResponse.onSuccess(logs);
    }
}