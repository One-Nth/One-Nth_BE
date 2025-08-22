package com.onenth.OneNth.domain.chat.dto;

import com.onenth.OneNth.domain.chat.entity.enums.ChatRoomType;
import lombok.*;

import java.time.LocalDateTime;

public class ChatResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomResponseDTO{
        Long ChatRoomId;
        String ChatRoomName;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomPreviewDTO {
        private Long chatRoomId;
        private ChatRoomType chatRoomType;
        private String chatRoomName;
        private Long opponentId;
        private String lastMessageContent;
        private LocalDateTime lastMessageTime;
        private Boolean blocked;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMessageDTO {
        private Long senderMemberId;
        private String content;
        private LocalDateTime messageTime;
    }
}
