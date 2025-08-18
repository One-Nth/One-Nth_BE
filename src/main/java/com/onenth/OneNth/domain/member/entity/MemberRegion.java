package com.onenth.OneNth.domain.member.entity;

import com.onenth.OneNth.domain.common.BaseEntity;
import com.onenth.OneNth.domain.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class MemberRegion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Builder.Default
    private boolean isMain = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean isVerified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateIsMain(boolean isMain) {
        this.isMain = isMain;
    }

    public void updateIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public static MemberRegion of(Member member, Region region) {
        return MemberRegion.builder()
                .member(member)
                .region(region)
                .build();
    }
}