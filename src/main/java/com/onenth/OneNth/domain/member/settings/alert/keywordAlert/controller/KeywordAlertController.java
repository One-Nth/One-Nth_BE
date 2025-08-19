package com.onenth.OneNth.domain.member.settings.alert.keywordAlert.controller;

import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.dto.KeywordAlertRequestDTO;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.dto.KeywordAlertResponseDTO;
import com.onenth.OneNth.domain.member.settings.alert.keywordAlert.service.KeywordAlertCommandService;
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

@Tag(name = "사용자 설정 - 키워드 알림 설정 관련 API", description = "키워드 알림 설정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-settings/keyword-alerts")
public class KeywordAlertController {

    private final KeywordAlertCommandService keywordAlertCommandService;

    @Operation(
            summary = "지역 키워드 알림 등록 API",
            description = "사용자 설정 중 지역 키워드를 알림으로 등록하는 API입니다. 응답으로 keywordAlertType(region/product), keywordAlertId와 keyword, 알림 활성화 여부(enabled)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 지역 키워드 알림 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REGION001", description = "존재하지 않는 지역입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT001", description = "등록 가능 지역 알림은 최대 3개입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT002", description = "이미 알림으로 등록한 지역입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PostMapping("/regions/{regionId}")
    public ApiResponse<KeywordAlertResponseDTO.AddKeywordAlertResponseDTO> addRegionKeywordAlert(
            @Parameter(hidden=true) @AuthUser Long userId,
            @PathVariable("regionId") Long regionId
    ) {
        return ApiResponse.onSuccess(keywordAlertCommandService.addRegionKeyword(userId, regionId));
    }

    @Operation(
            summary = "지역 키워드 알림 on/off API",
            description = "사용자 설정 중 지역 키워드 알림을 끄거나 켜는 API입니다. 응답으로 keywordAlertType(region/product), keywordAlertId와 keyword, 알림 활성화 여부(enabled)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 지역 키워드 알림 상태 업데이트 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT003", description = "해당 지역 키워드 알림이 존재하지 않거나 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PatchMapping("/regions/{regionKeywordAlertId}")
    public ApiResponse<KeywordAlertResponseDTO.SetKeywordAlertStatusResponseDTO> setRegionAlertStatus(
            @Parameter(hidden=true) @AuthUser Long userId,
            @PathVariable Long regionKeywordAlertId,
            @RequestBody KeywordAlertRequestDTO.SetRegionAlertStatusRequestDTO request
    ) {
        return ApiResponse.onSuccess(keywordAlertCommandService.setRegionAlertStatus(userId, regionKeywordAlertId, request));
    }

    @Operation(
            summary = "상품 키워드 알림 등록 API",
            description = "사용자 설정 중 상품 키워드를 알림으로 등록하는 API입니다. 응답으로 keywordAlertType(region/product), keywordAlertId, keyword, 알림 활성화 여부(enabled)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 키워드 알림 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT004", description = "등록 가능 키워드 알림은 최대 5개입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT005", description = "이미 알림으로 등록한 키워드입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PostMapping("")
    public ApiResponse<KeywordAlertResponseDTO.AddKeywordAlertResponseDTO> addKeywordAlert(
            @Parameter(hidden=true) @AuthUser Long userId,
            @Valid @RequestBody KeywordAlertRequestDTO.AddKeywordAlertRequestDTO request
    ) {
        return ApiResponse.onSuccess(keywordAlertCommandService.addProductKeyword(userId, request));
    }

    @Operation(
            summary = "상품 키워드 알림 on/off API",
            description = "사용자 설정 중 상품 키워드 알림을 끄거나 켜는 API입니다. 응답으로 keywordAlertType(region/product), keywordAlertId, keyword, 알림 활성화 여부(enabled)을 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 키워드 알림 상태 업데이트 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT006", description = "해당 키워드 알림이 존재하지 않거나 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PatchMapping("/{keywordAlertId}")
    public ApiResponse<KeywordAlertResponseDTO.SetKeywordAlertStatusResponseDTO> setKeywordAlertStatus(
            @Parameter(hidden=true) @AuthUser Long userId,
            @PathVariable Long keywordAlertId,
            @RequestBody KeywordAlertRequestDTO.SetKeywordAlertStatusRequestDTO request
    ) {
        return ApiResponse.onSuccess(keywordAlertCommandService.setProductKeywordAlertStatus(userId, keywordAlertId, request));
    }

    @Operation(
            summary = "키워드(지역, 상품 포함) 알림 목록 수정 API",
            description = "사용자 설정 중 키워드 알림 목록을 수정하는 API입니다. 응답으로 keywordAlertType(region/product), alertId, keyword, 알림 활성화 여부(enabled)를 하나의 객체로 갖는 리스트를 반환합니다." +
                    "이때 키워드 알림 목록은 키워드 타입(region/product)에 관계없이 가장 최근에 등록한 알림이 먼저 표시됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 키워드 알림 상태 업데이트 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER001", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT006", description = "해당 키워드 알림이 존재하지 않거나 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "KEYWORD_ALERT003", description = "해당 지역 키워드 알림이 존재하지 않거나 접근 권한이 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON500", description = "서버 에러, 관리자에게 문의 바랍니다", content = @Content(schema = @Schema(implementation = ErrorReasonDTO.class))),
    })
    @PatchMapping("")
    public ApiResponse<KeywordAlertResponseDTO.AlertListResponseDTO> updateKeywordAlertList(
            @Parameter(hidden=true) @AuthUser Long userId,
            @RequestBody KeywordAlertRequestDTO.UpdateKeywordAlertListRequestDTO request
    ) {
        return ApiResponse.onSuccess(keywordAlertCommandService.updateKeywordAlertList(userId, request));
    }
}
