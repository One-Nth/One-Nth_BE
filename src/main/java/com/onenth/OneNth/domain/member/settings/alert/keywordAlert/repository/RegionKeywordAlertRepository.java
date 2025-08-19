package com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository;

import com.onenth.OneNth.domain.member.entity.RegionKeywordAlert;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.region.entity.Region;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionKeywordAlertRepository extends JpaRepository<RegionKeywordAlert, Long> {

    int countByMember(Member member);

    boolean existsByMemberAndRegionKeyword(Member member, Region region);

    Optional<RegionKeywordAlert> findByIdAndMember(Long id, Member member);

    List<RegionKeywordAlert> findAllByMember(Member member);

    @EntityGraph(attributePaths = {"member"})
    List<RegionKeywordAlert> findByRegionKeywordAndEnabled(Region regionKeyword, boolean enabled);
}
