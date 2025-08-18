package com.onenth.OneNth.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseItem is a Querydsl query type for PurchaseItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseItem extends EntityPathBase<PurchaseItem> {

    private static final long serialVersionUID = 1482688227L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseItem purchaseItem = new QPurchaseItem("purchaseItem");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.ItemCategory> itemCategory = createEnum("itemCategory", com.onenth.OneNth.domain.product.entity.enums.ItemCategory.class);

    public final ListPath<ItemImage, QItemImage> itemImages = this.<ItemImage, QItemImage>createList("itemImages", ItemImage.class, QItemImage.class, PathInits.DIRECT2);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath purchaseLocation = createString("purchaseLocation");

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.PurchaseMethod> purchaseMethod = createEnum("purchaseMethod", com.onenth.OneNth.domain.product.entity.enums.PurchaseMethod.class);

    public final ListPath<com.onenth.OneNth.domain.product.entity.review.PurchaseReview, com.onenth.OneNth.domain.product.entity.review.QPurchaseReview> purchaseReviews = this.<com.onenth.OneNth.domain.product.entity.review.PurchaseReview, com.onenth.OneNth.domain.product.entity.review.QPurchaseReview>createList("purchaseReviews", com.onenth.OneNth.domain.product.entity.review.PurchaseReview.class, com.onenth.OneNth.domain.product.entity.review.QPurchaseReview.class, PathInits.DIRECT2);

    public final StringPath purchaseUrl = createString("purchaseUrl");

    public final com.onenth.OneNth.domain.region.entity.QRegion region;

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.Status> status = createEnum("status", com.onenth.OneNth.domain.product.entity.enums.Status.class);

    public final ListPath<Tag, QTag> tags = this.<Tag, QTag>createList("tags", Tag.class, QTag.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPurchaseItem(String variable) {
        this(PurchaseItem.class, forVariable(variable), INITS);
    }

    public QPurchaseItem(Path<? extends PurchaseItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseItem(PathMetadata metadata, PathInits inits) {
        this(PurchaseItem.class, metadata, inits);
    }

    public QPurchaseItem(Class<? extends PurchaseItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.region = inits.isInitialized("region") ? new com.onenth.OneNth.domain.region.entity.QRegion(forProperty("region")) : null;
    }

}

