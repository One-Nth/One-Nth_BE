package com.onenth.OneNth.domain.product.entity.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharingReviewImage is a Querydsl query type for SharingReviewImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSharingReviewImage extends EntityPathBase<SharingReviewImage> {

    private static final long serialVersionUID = -1264706002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharingReviewImage sharingReviewImage = new QSharingReviewImage("sharingReviewImage");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QSharingReview sharingReview;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSharingReviewImage(String variable) {
        this(SharingReviewImage.class, forVariable(variable), INITS);
    }

    public QSharingReviewImage(Path<? extends SharingReviewImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharingReviewImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharingReviewImage(PathMetadata metadata, PathInits inits) {
        this(SharingReviewImage.class, metadata, inits);
    }

    public QSharingReviewImage(Class<? extends SharingReviewImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sharingReview = inits.isInitialized("sharingReview") ? new QSharingReview(forProperty("sharingReview"), inits.get("sharingReview")) : null;
    }

}

