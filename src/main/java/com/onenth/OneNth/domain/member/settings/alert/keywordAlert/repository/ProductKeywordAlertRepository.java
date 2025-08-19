package com.onenth.OneNth.domain.member.settings.alert.keywordAlert.repository;

import com.onenth.OneNth.domain.member.entity.ProductKeywordAlert;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.RegionKeywordAlert;
import com.onenth.OneNth.domain.region.entity.Region;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductKeywordAlertRepository extends JpaRepository<ProductKeywordAlert, Long> {

    int countByMember(Member member);

    boolean existsByMemberAndKeyword(Member member, String keyword);

    Optional<ProductKeywordAlert> findByIdAndMember(Long id, Member member);

    List<ProductKeywordAlert> findAllByMember(Member member);

    @EntityGraph(attributePaths = {"member"})
    List<ProductKeywordAlert> findByKeywordAndEnabled(String keyword, boolean enabled);
}
