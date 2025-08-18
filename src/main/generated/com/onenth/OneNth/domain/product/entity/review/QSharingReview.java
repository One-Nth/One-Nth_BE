package com.onenth.OneNth.domain.product.entity.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharingReview is a Querydsl query type for SharingReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSharingReview extends EntityPathBase<SharingReview> {

    private static final long serialVersionUID = -835383507L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharingReview sharingReview = new QSharingReview("sharingReview");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final NumberPath<java.math.BigDecimal> rate = createNumber("rate", java.math.BigDecimal.class);

    public final ListPath<SharingReviewImage, QSharingReviewImage> reviewImages = this.<SharingReviewImage, QSharingReviewImage>createList("reviewImages", SharingReviewImage.class, QSharingReviewImage.class, PathInits.DIRECT2);

    public final com.onenth.OneNth.domain.product.entity.QSharingItem sharingItem;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSharingReview(String variable) {
        this(SharingReview.class, forVariable(variable), INITS);
    }

    public QSharingReview(Path<? extends SharingReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharingReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharingReview(PathMetadata metadata, PathInits inits) {
        this(SharingReview.class, metadata, inits);
    }

    public QSharingReview(Class<? extends SharingReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.sharingItem = inits.isInitialized("sharingItem") ? new com.onenth.OneNth.domain.product.entity.QSharingItem(forProperty("sharingItem"), inits.get("sharingItem")) : null;
    }

}

