package com.onenth.OneNth.domain.member.settings.alert.generalAlert.service;

import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertRequestDTO;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto.GeneralAlertResponseDTO;

public interface GeneralAlertCommandService {

/*    GeneralAlertResponseDTO.SetScrapAlertStatusResponseDTO setScrapAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetScrapAlertStatusRequestDTO request
    );*/

    GeneralAlertResponseDTO.SetReviewAlertStatusResponseDTO setReviewAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetReviewAlertStatusRequestDTO request
    );

    GeneralAlertResponseDTO.SetChatAlertStatusResponseDTO setChatAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetChatAlertStatusRequestDTO request
    );

    GeneralAlertResponseDTO.SetCommentAlertStatusResponseDTO setCommentAlertStatus(
            Long userId,
            GeneralAlertRequestDTO.SetCommentAlertStatusRequestDTO request
    );

}
