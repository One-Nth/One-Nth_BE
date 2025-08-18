package com.onenth.OneNth.domain.member.service.memberService;

import com.onenth.OneNth.domain.member.converter.MemberConverter;
import com.onenth.OneNth.domain.member.dto.MemberRequestDTO;
import com.onenth.OneNth.domain.member.dto.MemberResponseDTO;
import com.onenth.OneNth.domain.member.entity.Block;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.MemberAlertSetting;
import com.onenth.OneNth.domain.member.entity.enums.MemberStatus;
import com.onenth.OneNth.domain.member.settings.alert.generalAlert.repository.MemberAlertSettingRepository;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.member.service.EmailVerificationService.EmailService;
import com.onenth.OneNth.domain.member.service.EmailVerificationService.EmailVerificationService;
import com.onenth.OneNth.domain.member.settings.block.repository.BlockRepository;
import com.onenth.OneNth.domain.post.entity.Like;
import com.onenth.OneNth.domain.post.entity.Post;
import com.onenth.OneNth.domain.post.entity.Scrap;
import com.onenth.OneNth.domain.post.repository.PostRepository;
import com.onenth.OneNth.domain.post.repository.likeRepository.LikeRepository;
import com.onenth.OneNth.domain.post.repository.scrapRepository.ScrapRepository;
import com.onenth.OneNth.domain.region.entity.Region;
import com.onenth.OneNth.domain.region.repository.RegionRepository;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.handler.MemberHandler;
import com.onenth.OneNth.global.configuration.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostRepository postRepository;
    private final ScrapRepository scrapRepository;
    private final LikeRepository likeRepository;
    private final BlockRepository blockRepository;
    private final MemberAlertSettingRepository memberAlertSettingRepository;
    @Override
    public MemberResponseDTO.SignupResultDTO signupMember(MemberRequestDTO.SignupDTO request) {

        //이메일 인증 여부 우선 확인
        if (!emailVerificationService.isVerified(request.getEmail(), "signup")) {
            throw new RuntimeException("이메일 인증을 먼저 완료해주세요");
        }

        // 1. Password 와 confirmPassword 값 일치 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password 와 ConfirmPassword 의 값이 일치하지 않습니다.");
        }

        // 2. 지역명으로 Region 조회
        Region region = regionRepository.findByRegionName(request.getRegionName())
                .orElseThrow(() -> new RuntimeException("해당 지역이 존재하지 않습니다."));

        // 3. Member + MemberRegion 생성
        Member member = MemberConverter.toMember(request, region);

        // 4. 비밀번호 암호화
        member.encodePassword(passwordEncoder.encode(request.getPassword()));

        // 5. 회원 저장
        Member savedMember = memberRepository.save(member);

        // 알림설정 생성 (기본값 ON)
        MemberAlertSetting alertSetting = MemberAlertSetting.builder()
                .member(savedMember)
                .chatAlerts(true)
                .scrapAlerts(true)
                .reviewAlerts(true)
                .commentAlerts(true)
                .build();
        memberAlertSettingRepository.save(alertSetting);

        return MemberConverter.toSignupResultDTO(savedMember);
    }

    @Override
    public MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request) {
        Member member = memberRepository.findByEmailAndStatus(request.getEmail(), MemberStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("존재하지 않거나 탈퇴한 회원입니다."));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getId().toString(),
                null,
                Collections.emptyList()
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        return MemberConverter.toLoginResultDTO(
                member.getId(),
                accessToken,
                refreshToken
        );
    }

    @Override
    public MemberResponseDTO.PasswordResetResultDTO resetPassword(MemberRequestDTO.ResetPasswordRequestDTO request) {
        //이메일 인증 여부 확인
        if (!emailVerificationService.isVerified(request.getEmail(), "reset_password")) {
            throw new RuntimeException("이메일 인증이 완료되지 않았습니다.");
        }

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        // 새 비밀번호 암호화 저장
        member.setPassword(passwordEncoder.encode(request.getNewPassword()));
        memberRepository.save(member);

        return MemberResponseDTO.PasswordResetResultDTO.builder().isSuccess(true).build();
    }


    @Override
    public MemberResponseDTO.AddScrapOrLikeResponseDTO addScrap(Long memberId, Long postId) {
        // 게시글 존재 여부 확인
        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        // 중복 스크랩 여부 확인
        boolean exists = scrapRepository.existsByPostIdAndMemberId(postId, memberId);
        if (exists) {
            throw new RuntimeException("이미 스크랩한 글입니다.");
        }

        // 회원 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        Scrap scrap = Scrap.builder()
                .member(member)
                .post(Post.builder().id(postId).build())
                .build();

        scrapRepository.save(scrap);

        return MemberResponseDTO.AddScrapOrLikeResponseDTO.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    public MemberResponseDTO.CancelScrapOrLikeResponseDTO cancelScrap(Long memberId, Long postId) {
        Scrap scrap = scrapRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new RuntimeException("해당 멤버가 스크랩한 글이 없습니다."));

        scrapRepository.delete(scrap);

        return MemberResponseDTO.CancelScrapOrLikeResponseDTO.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    public MemberResponseDTO.AddScrapOrLikeResponseDTO addLike(Long memberId, Long postId) {
        // 게시글 존재 여부 확인
        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        // 중복 공감 여부 확인
        boolean exists = likeRepository.findByMemberIdAndPostId(memberId, postId).isPresent();
        if (exists) {
            throw new RuntimeException("이미 공감한 글입니다.");
        }

        // 회원 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        Like like = Like.builder()
                .member(member)
                .post(Post.builder().id(postId).build())
                .build();

        likeRepository.save(like);

        return MemberResponseDTO.AddScrapOrLikeResponseDTO.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    public MemberResponseDTO.CancelScrapOrLikeResponseDTO cancelLike(Long memberId, Long postId) {
        Like like = likeRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new RuntimeException("해당 멤버가 공감한 글이 없습니다."));

        likeRepository.delete(like);

        return MemberResponseDTO.CancelScrapOrLikeResponseDTO.builder()
                .isSuccess(true)
                .build();
    }

    @Transactional
    @Override
    public void withdrawMember(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        member.setStatus(MemberStatus.INACTIVE);
        member.setInactiveDate(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void blockMember(Long memberId, Long targetmemberId) {
        if (memberId.equals(targetmemberId)) {
            throw new MemberHandler(ErrorStatus.MEMBER_CANNOT_BLOCK_SELF);
        }
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Member blockedMember = memberRepository.findById(targetmemberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        blockRepository.save(Block.builder()
                        .member(member)
                        .blockedMember(blockedMember).build());
    }
}
