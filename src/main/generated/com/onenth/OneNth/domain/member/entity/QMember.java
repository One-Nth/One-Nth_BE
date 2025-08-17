package com.onenth.OneNth.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1798981170L;

    public static final QMember member = new QMember("member1");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final ListPath<com.onenth.OneNth.domain.alert.entity.Alert, com.onenth.OneNth.domain.alert.entity.QAlert> alerts = this.<com.onenth.OneNth.domain.alert.entity.Alert, com.onenth.OneNth.domain.alert.entity.QAlert>createList("alerts", com.onenth.OneNth.domain.alert.entity.Alert.class, com.onenth.OneNth.domain.alert.entity.QAlert.class, PathInits.DIRECT2);

    public final ListPath<com.onenth.OneNth.domain.chat.entity.ChatRoomMember, com.onenth.OneNth.domain.chat.entity.QChatRoomMember> chatRoomMembers = this.<com.onenth.OneNth.domain.chat.entity.ChatRoomMember, com.onenth.OneNth.domain.chat.entity.QChatRoomMember>createList("chatRoomMembers", com.onenth.OneNth.domain.chat.entity.ChatRoomMember.class, com.onenth.OneNth.domain.chat.entity.QChatRoomMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> inactiveDate = createDateTime("inactiveDate", java.time.LocalDateTime.class);

    public final EnumPath<com.onenth.OneNth.domain.member.entity.enums.LoginType> loginType = createEnum("loginType", com.onenth.OneNth.domain.member.entity.enums.LoginType.class);

    public final BooleanPath marketingAgree = createBoolean("marketingAgree");

    public final ListPath<MemberRegion, QMemberRegion> memberRegions = this.<MemberRegion, QMemberRegion>createList("memberRegions", MemberRegion.class, QMemberRegion.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.onenth.OneNth.domain.post.entity.PostComment, com.onenth.OneNth.domain.post.entity.QPostComment> postComments = this.<com.onenth.OneNth.domain.post.entity.PostComment, com.onenth.OneNth.domain.post.entity.QPostComment>createList("postComments", com.onenth.OneNth.domain.post.entity.PostComment.class, com.onenth.OneNth.domain.post.entity.QPostComment.class, PathInits.DIRECT2);

    public final ListPath<com.onenth.OneNth.domain.post.entity.Post, com.onenth.OneNth.domain.post.entity.QPost> posts = this.<com.onenth.OneNth.domain.post.entity.Post, com.onenth.OneNth.domain.post.entity.QPost>createList("posts", com.onenth.OneNth.domain.post.entity.Post.class, com.onenth.OneNth.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final ListPath<com.onenth.OneNth.domain.product.entity.PurchaseItem, com.onenth.OneNth.domain.product.entity.QPurchaseItem> purchaseItems = this.<com.onenth.OneNth.domain.product.entity.PurchaseItem, com.onenth.OneNth.domain.product.entity.QPurchaseItem>createList("purchaseItems", com.onenth.OneNth.domain.product.entity.PurchaseItem.class, com.onenth.OneNth.domain.product.entity.QPurchaseItem.class, PathInits.DIRECT2);

    public final ListPath<com.onenth.OneNth.domain.product.entity.review.PurchaseReview, com.onenth.OneNth.domain.product.entity.review.QPurchaseReview> purchaseReviews = this.<com.onenth.OneNth.domain.product.entity.review.PurchaseReview, com.onenth.OneNth.domain.product.entity.review.QPurchaseReview>createList("purchaseReviews", com.onenth.OneNth.domain.product.entity.review.PurchaseReview.class, com.onenth.OneNth.domain.product.entity.review.QPurchaseReview.class, PathInits.DIRECT2);

    public final ListPath<com.onenth.OneNth.domain.product.entity.SharingItem, com.onenth.OneNth.domain.product.entity.QSharingItem> sharingItems = this.<com.onenth.OneNth.domain.product.entity.SharingItem, com.onenth.OneNth.domain.product.entity.QSharingItem>createList("sharingItems", com.onenth.OneNth.domain.product.entity.SharingItem.class, com.onenth.OneNth.domain.product.entity.QSharingItem.class, PathInits.DIRECT2);

    public final ListPath<com.onenth.OneNth.domain.product.entity.review.SharingReview, com.onenth.OneNth.domain.product.entity.review.QSharingReview> sharingReviews = this.<com.onenth.OneNth.domain.product.entity.review.SharingReview, com.onenth.OneNth.domain.product.entity.review.QSharingReview>createList("sharingReviews", com.onenth.OneNth.domain.product.entity.review.SharingReview.class, com.onenth.OneNth.domain.product.entity.review.QSharingReview.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.onenth.OneNth.domain.member.entity.enums.MemberStatus> status = createEnum("status", com.onenth.OneNth.domain.member.entity.enums.MemberStatus.class);

    public final NumberPath<Long> totalSavedAmount = createNumber("totalSavedAmount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final BooleanPath verified = createBoolean("verified");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

