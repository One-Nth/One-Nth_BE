package com.onenth.OneNth.domain.alert.converter;

import com.onenth.OneNth.domain.alert.dto.AlertResponseDTO;
import com.onenth.OneNth.domain.alert.entity.Alert;
import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.product.entity.enums.ItemType;

public class AlertConverter {

    public static AlertResponseDTO.DealNotificationResponseDTO toDealNotificationResponseDTO(Alert alert) {
        return AlertResponseDTO.DealNotificationResponseDTO.builder()
                .alertType(alert.getAlertType())
                .itemType(alert.getItemType())
                .contentId(alert.getContentId())
                .message(alert.getMessage())
                .readStatus(alert.isRead())
                .build();
    }

    public static AlertResponseDTO.PostNotificationResponseDTO toPostNotificationResponseDTO(Alert alert) {
        return AlertResponseDTO.PostNotificationResponseDTO.builder()
                .alertType(alert.getAlertType())
                .contentId(alert.getContentId())
                .message(alert.getMessage())
                .readStatus(alert.isRead())
                .build();
    }

    public static Alert toAlert(
            Member member, AlertType alertType, ItemType itemType,
            Long contentId, String message, String title){
        return Alert.builder()
                .alertType(alertType)
                .itemType(itemType)
                .contentId(contentId)
                .message(message)
                .title(title)
                .isRead(false)
                .member(member)
                .build();
    }
}
