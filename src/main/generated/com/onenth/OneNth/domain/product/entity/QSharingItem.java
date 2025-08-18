package com.onenth.OneNth.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharingItem is a Querydsl query type for SharingItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSharingItem extends EntityPathBase<SharingItem> {

    private static final long serialVersionUID = 602141952L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharingItem sharingItem = new QSharingItem("sharingItem");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAvailable = createBoolean("isAvailable");

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.ItemCategory> itemCategory = createEnum("itemCategory", com.onenth.OneNth.domain.product.entity.enums.ItemCategory.class);

    public final ListPath<ItemImage, QItemImage> itemImages = this.<ItemImage, QItemImage>createList("itemImages", ItemImage.class, QItemImage.class, PathInits.DIRECT2);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.PurchaseMethod> purchaseMethod = createEnum("purchaseMethod", com.onenth.OneNth.domain.product.entity.enums.PurchaseMethod.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final com.onenth.OneNth.domain.region.entity.QRegion region;

    public final StringPath sharingLocation = createString("sharingLocation");

    public final ListPath<com.onenth.OneNth.domain.product.entity.review.SharingReview, com.onenth.OneNth.domain.product.entity.review.QSharingReview> sharingReviews = this.<com.onenth.OneNth.domain.product.entity.review.SharingReview, com.onenth.OneNth.domain.product.entity.review.QSharingReview>createList("sharingReviews", com.onenth.OneNth.domain.product.entity.review.SharingReview.class, com.onenth.OneNth.domain.product.entity.review.QSharingReview.class, PathInits.DIRECT2);

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.Status> status = createEnum("status", com.onenth.OneNth.domain.product.entity.enums.Status.class);

    public final ListPath<Tag, QTag> tags = this.<Tag, QTag>createList("tags", Tag.class, QTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSharingItem(String variable) {
        this(SharingItem.class, forVariable(variable), INITS);
    }

    public QSharingItem(Path<? extends SharingItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharingItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharingItem(PathMetadata metadata, PathInits inits) {
        this(SharingItem.class, metadata, inits);
    }

    public QSharingItem(Class<? extends SharingItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.region = inits.isInitialized("region") ? new com.onenth.OneNth.domain.region.entity.QRegion(forProperty("region")) : null;
    }

}

