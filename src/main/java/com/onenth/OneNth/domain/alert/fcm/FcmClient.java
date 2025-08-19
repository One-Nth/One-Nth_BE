package com.onenth.OneNth.domain.alert.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FcmClient {

    public static void sendNotification(String token, String title, String body) {

        Message message = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("value", body)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 메시지 전송 완료: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 메시지 전송 실패", e);
        }
    }
}
