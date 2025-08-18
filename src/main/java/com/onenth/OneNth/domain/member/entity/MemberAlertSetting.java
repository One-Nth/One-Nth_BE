package com.onenth.OneNth.domain.member.entity;

import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAlertSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean chatAlerts = false; // 채팅알림 수신여부

    @Column(nullable = false)
    private boolean reviewAlerts = false; // 리뷰알림 수신여부

    @Column(nullable = false)
    private boolean scrapAlerts = false; // 스크랩알림 수신여부

    @Column(nullable = false)
    private boolean commentAlerts = false; // 게시물 댓글 알림 수신여부

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    public void enableAlerts(AlertType alertType) {
        switch (alertType) {
            case CHAT -> chatAlerts = true;
            case REVIEW -> reviewAlerts = true;
            case COMMENT -> commentAlerts = true;
/*            case SCRAP -> scrapAlerts = true;*/
        }
    }

    public void disableAlerts(AlertType alertType) {
        switch (alertType) {
            case CHAT -> chatAlerts = false;
            case REVIEW -> reviewAlerts = false;
            case COMMENT -> commentAlerts = false;
/*            case SCRAP -> scrapAlerts = false;*/
        }
    }

}