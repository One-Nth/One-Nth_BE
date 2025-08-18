package com.onenth.OneNth.domain.member.settings.alert.generalAlert.controller;

import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertRequestDTO;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertResponseDTO;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.service.GeneralAlertCommandService;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.service.GeneralAlertQueryService;
import com.onenth.OneNth.global.apiPayload.ApiResponse;
import com.onenth.OneNth.global.apiPayload.code.ErrorReasonDTO;
import com.onenth.OneNth.global.auth.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 설정 - 일반 알림 설정 관련 API", description = "키워드 알림 외 알림 설정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-settings")
public class GeneralAlertController {

    private final GeneralAlertCommandService generalAlertCommandService;
    private final GeneralAlertQueryService generalAlertQueryService;

/*    @Operation(
            summary =  "스크랩 알림 on/off API",
            description = "사용자 설정 중 스크랩 알림 설정 on/off API입니다. 응답으로 alertType(스크랩)과 enabled(활성 여부)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 스크랩 알람 설정 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ALERT_SETTING001", description = "해당 사용자의 알림 설정 정보가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PostMapping("/scrap-alerts")
    public ApiResponse<GeneralAlertResponseDTO.SetScrapAlertStatusResponseDTO> setScrapAlertStatus(
            @Parameter(hidden=true) @AuthUser Long userId,
            @Valid @RequestBody GeneralAlertRequestDTO.SetScrapAlertStatusRequestDTO request
    ) {
        return ApiResponse.onSuccess(generalAlertCommandService.setScrapAlertStatus(userId, request));
    }*/

    @Operation(
            summary =  "리뷰 알림 on/off API",
            description = "사용자 설정 중 리뷰 알림 설정 on/off API입니다. 응답으로 alertType(리뷰)과 enabled(활성 여부)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 리뷰 알람 설정 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ALERT_SETTING001", description = "해당 사용자의 알림 설정 정보가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PostMapping("/review-alerts")
    public ApiResponse<GeneralAlertResponseDTO.SetReviewAlertStatusResponseDTO> setReviewAlertStatus(
            @Parameter(hidden=true) @AuthUser Long userId,
            @Valid @RequestBody GeneralAlertRequestDTO.SetReviewAlertStatusRequestDTO request
    ) {
        return ApiResponse.onSuccess(generalAlertCommandService.setReviewAlertStatus(userId, request));
    }

    @Operation(
            summary =  "채팅 전체 알림 on/off API",
            description = "사용자 설정 중 채팅 전체 알림 설정 on/off API입니다. 응답으로 alertType(채팅)과 enabled(활성 여부)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 채팅 알람 설정 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ALERT_SETTING001", description = "해당 사용자의 알림 설정 정보가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PostMapping("/chat-alerts")
    public ApiResponse<GeneralAlertResponseDTO.SetChatAlertStatusResponseDTO> setChatAlertStatus(
            @Parameter(hidden=true) @AuthUser Long userId,
            @Valid @RequestBody GeneralAlertRequestDTO.SetChatAlertStatusRequestDTO request
    ) {
        return ApiResponse.onSuccess(generalAlertCommandService.setChatAlertStatus(userId, request));
    }

    @Operation(
            summary =  "게시글 댓글 알림 on/off API",
            description = "사용자 설정 중 게시글 댓글 알림 설정 on/off API입니다. 응답으로 alertType(COMMENT)과 enabled(활성 여부)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 댓글 알람 설정 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ALERT_SETTING001", description = "해당 사용자의 알림 설정 정보가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PostMapping("/comment-alerts")
    public ApiResponse<GeneralAlertResponseDTO.SetCommentAlertStatusResponseDTO> setCommentAlertStatus(
            @Parameter(hidden=true) @AuthUser Long userId,
            @Valid @RequestBody GeneralAlertRequestDTO.SetCommentAlertStatusRequestDTO request
    ) {
        return ApiResponse.onSuccess(generalAlertCommandService.setCommentAlertStatus(userId, request));
    }

    @Operation(
            summary =  "알림 설정 전체 조회 API",
            description = "사용자 설정 중 모든 알림 설정을 조회하는 API입니다. 응답으로 스크랩 알림 활성화 여부, 리뷰 알림 활성화 여부, 키워드 알림 목록 및 활성화 여부를 포함한 리스트을 반환합니다. " +
                    "이때 키워드 알림 목록은 키워드 타입(region/product)에 관계없이 가장 최근에 등록한 알림이 먼저 표시됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 알람 설정 전체 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ALERT_SETTING001", description = "해당 사용자의 알림 설정 정보가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @GetMapping("")
    public ApiResponse<GeneralAlertResponseDTO.GetAllAlertSettingsResponseDTO> getAllAlertSettings(
            @Parameter(hidden=true) @AuthUser Long userId
    ) {
        return ApiResponse.onSuccess(generalAlertQueryService.getAllAlertSettings(userId));
    }
}
