package com.onenth.OneNth.domain.product.entity.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseReview is a Querydsl query type for PurchaseReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseReview extends EntityPathBase<PurchaseReview> {

    private static final long serialVersionUID = 1735134976L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseReview purchaseReview = new QPurchaseReview("purchaseReview");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final com.onenth.OneNth.domain.product.entity.QPurchaseItem purchaseItem;

    public final NumberPath<java.math.BigDecimal> rate = createNumber("rate", java.math.BigDecimal.class);

    public final ListPath<PurchaseReviewImage, QPurchaseReviewImage> reviewImages = this.<PurchaseReviewImage, QPurchaseReviewImage>createList("reviewImages", PurchaseReviewImage.class, QPurchaseReviewImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPurchaseReview(String variable) {
        this(PurchaseReview.class, forVariable(variable), INITS);
    }

    public QPurchaseReview(Path<? extends PurchaseReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseReview(PathMetadata metadata, PathInits inits) {
        this(PurchaseReview.class, metadata, inits);
    }

    public QPurchaseReview(Class<? extends PurchaseReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.purchaseItem = inits.isInitialized("purchaseItem") ? new com.onenth.OneNth.domain.product.entity.QPurchaseItem(forProperty("purchaseItem"), inits.get("purchaseItem")) : null;
    }

}

