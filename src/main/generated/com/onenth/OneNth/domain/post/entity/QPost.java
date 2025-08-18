package com.onenth.OneNth.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -2096716518L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final ListPath<Like, QLike> like = this.<Like, QLike>createList("like", Like.class, QLike.class, PathInits.DIRECT2);

    public final StringPath link = createString("link");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final StringPath placeName = createString("placeName");

    public final ListPath<PostComment, QPostComment> postComment = this.<PostComment, QPostComment>createList("postComment", PostComment.class, QPostComment.class, PathInits.DIRECT2);

    public final ListPath<PostImage, QPostImage> postImages = this.<PostImage, QPostImage>createList("postImages", PostImage.class, QPostImage.class, PathInits.DIRECT2);

    public final ListPath<PostTag, QPostTag> postTag = this.<PostTag, QPostTag>createList("postTag", PostTag.class, QPostTag.class, PathInits.DIRECT2);

    public final EnumPath<com.onenth.OneNth.domain.post.entity.enums.PostType> postType = createEnum("postType", com.onenth.OneNth.domain.post.entity.enums.PostType.class);

    public final com.onenth.OneNth.domain.region.entity.QRegion region;

    public final StringPath regionName = createString("regionName");

    public final ListPath<Scrap, QScrap> scrap = this.<Scrap, QScrap>createList("scrap", Scrap.class, QScrap.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.region = inits.isInitialized("region") ? new com.onenth.OneNth.domain.region.entity.QRegion(forProperty("region")) : null;
    }

}

