package com.onenth.OneNth.domain.post.service;

import com.onenth.OneNth.domain.alert.entity.AlertType;
import com.onenth.OneNth.domain.alert.repository.AlertRepository;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.post.dto.PostCommentSaveRequestDTO;
import com.onenth.OneNth.domain.post.entity.Post;
import com.onenth.OneNth.domain.post.entity.PostComment;
import com.onenth.OneNth.domain.post.entity.enums.PostType;
import com.onenth.OneNth.domain.post.repository.PostRepository;
import com.onenth.OneNth.domain.post.repository.commentRepository.CommentRepository;
import com.onenth.OneNth.domain.product.entity.enums.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.onenth.OneNth.domain.alert.converter.AlertConverter.toAlert;
import static com.onenth.OneNth.domain.alert.entity.AlertType.REVIEW;
import static com.onenth.OneNth.domain.alert.fcm.FcmClient.sendNotification;

@Service
@RequiredArgsConstructor
public class PostCommentCommandServiceImpl implements PostCommentCommandService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AlertRepository alertRepository;

    @Override
    @Transactional
    public Long saveComment(Long postId, Long memberId, PostCommentSaveRequestDTO requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        PostComment comment = PostComment.builder()
                .post(post)
                .member(member)
                .content(requestDto.getContent())
                .build();

        commentRepository.saveAndFlush(comment);

        Member targetUser = post.getMember();
        if (targetUser.getMemberAlertSetting().isCommentAlerts()) {
            String title = "새로운 댓글이 달렸어요 💬";
            String body = member.getName() + "님이 \"" + post.getTitle() + "\" 에 댓글을 남겼습니다.";

            targetUser.getFcmTokens().forEach(token ->
                    sendNotification(token.getFcmToken(), title, body)
            );

            AlertType alertType = switch (post.getPostType()) {
                case DISCOUNT -> AlertType.DISCOUNT;
                case RESTAURANT -> AlertType.RESTAURANT;
                case LIFE_TIP -> AlertType.LIFE_TIP;
            };
            alertRepository.save(toAlert(targetUser, alertType, null, post.getId(), body, title));

        }
        return comment.getId();
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new SecurityException("본인의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public PostComment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}
