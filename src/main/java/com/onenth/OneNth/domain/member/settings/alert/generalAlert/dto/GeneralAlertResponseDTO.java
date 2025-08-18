package com.onenth.OneNth.domain.member.settings.alert.generalAlert.dto;

import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.member.entity.enums.KeywordAlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GeneralAlertResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SetScrapAlertStatusResponseDTO {
        AlertType alertType;
        boolean enabled;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SetReviewAlertStatusResponseDTO {
        AlertType alertType;
        boolean enabled;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SetChatAlertStatusResponseDTO {
        AlertType alertType;
        boolean enabled;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SetCommentAlertStatusResponseDTO {
        AlertType alertType;
        boolean enabled;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAllAlertSettingsResponseDTO {
        GeneralAlertSummary reviewAlertSummary;
        List<KeywordAlertSummary> keywordAlertSummaryList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GeneralAlertSummary {
        AlertType alertType;
        boolean enabled;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KeywordAlertSummary {
        KeywordAlertType keywordAlertType;
        Long keywordAlertId;
        String keyword;
        boolean enabled;
    }
}
