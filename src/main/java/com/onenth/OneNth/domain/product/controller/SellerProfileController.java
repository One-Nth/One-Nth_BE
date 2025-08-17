package com.onenth.OneNth.domain.product.controller;

import com.onenth.OneNth.domain.product.dto.SellerProfileResponseDTO;
import com.onenth.OneNth.domain.product.service.SellerProfileService.getSellerProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.onenth.OneNth.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "같이사요 & 함께 나눠요 상품 판매자 관련 API", description = "판매자 프로필 조회 API 지원")
public class SellerProfileController {

    private final getSellerProfile sellerProfileService;

    @Operation(
            summary = "같이 사요 판매자 프로필 조회",
            description = """
            판매자의 프로필 정보를 조회합니다.
            - 닉네임, 본인 인증 여부, 대표 지역, 등록한 상품, 거래 후기 포함
        """
    )
    @GetMapping("group-purchases/{userId}")
    public ApiResponse<SellerProfileResponseDTO> getPurchaseSellerProfile(
            @Parameter(description = "조회할 판매자의 ID", required = true)
            @PathVariable("userId") Long userId
    ) {
        SellerProfileResponseDTO dto = sellerProfileService.getPurchaseSellerProfile(userId);
        return ApiResponse.onSuccess(dto);
    }

    @Operation(
            summary = "함께 나눠요 판매자 프로필 조회",
            description = """
            판매자의 프로필 정보를 조회합니다.
            - 닉네임, 본인 인증 여부, 대표 지역, 등록한 함께나눠요 상품, 구매자 거래 후기 포함
            """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/sharings/{userId}")
    public ApiResponse<SellerProfileResponseDTO> getSharingSellerProfile(
            @Parameter(description = "조회할 판매자의 ID", required = true)
            @PathVariable("userId") Long userId
    ) {
        SellerProfileResponseDTO dto = sellerProfileService.getSharingSellerProfile(userId);
        return ApiResponse.onSuccess(dto);
    }

    @Operation(
            summary = "판매자 프로필 거래 내역 및 총 별점 조회",
            description = """
            - 판매자의 프로필에서 해당 판매자의 거래 내역(거래 수) 및 총 별점을 반환합니다.
            - userId 에 조회하고자 하는 유저의 Id를 넣어주세요
            """
    )
    @GetMapping("/trade-history/{userId}")
    public ApiResponse<SellerProfileResponseDTO.TradeHistoryResponseDTO> getTradeHistory(
            @Parameter(description = "조회할 판매자의 ID", required = true)
            @PathVariable("userId") Long userId
    ) {
        SellerProfileResponseDTO.TradeHistoryResponseDTO result = sellerProfileService.countUserReceivedReviews(userId);
        return ApiResponse.onSuccess(result);
    }
}


