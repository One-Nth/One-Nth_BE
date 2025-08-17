package com.onenth.OneNth.domain.product.entity.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseReviewImage is a Querydsl query type for PurchaseReviewImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseReviewImage extends EntityPathBase<PurchaseReviewImage> {

    private static final long serialVersionUID = -117634501L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseReviewImage purchaseReviewImage = new QPurchaseReviewImage("purchaseReviewImage");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QPurchaseReview purchaseReview;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPurchaseReviewImage(String variable) {
        this(PurchaseReviewImage.class, forVariable(variable), INITS);
    }

    public QPurchaseReviewImage(Path<? extends PurchaseReviewImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseReviewImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseReviewImage(PathMetadata metadata, PathInits inits) {
        this(PurchaseReviewImage.class, metadata, inits);
    }

    public QPurchaseReviewImage(Class<? extends PurchaseReviewImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.purchaseReview = inits.isInitialized("purchaseReview") ? new QPurchaseReview(forProperty("purchaseReview"), inits.get("purchaseReview")) : null;
    }

}

