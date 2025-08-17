package com.onenth.OneNth.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemImage is a Querydsl query type for ItemImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemImage extends EntityPathBase<ItemImage> {

    private static final long serialVersionUID = 891362617L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemImage itemImage = new QItemImage("itemImage");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.ItemType> itemType = createEnum("itemType", com.onenth.OneNth.domain.product.entity.enums.ItemType.class);

    public final QPurchaseItem purchaseItem;

    public final QSharingItem sharingItem;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath url = createString("url");

    public QItemImage(String variable) {
        this(ItemImage.class, forVariable(variable), INITS);
    }

    public QItemImage(Path<? extends ItemImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemImage(PathMetadata metadata, PathInits inits) {
        this(ItemImage.class, metadata, inits);
    }

    public QItemImage(Class<? extends ItemImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.purchaseItem = inits.isInitialized("purchaseItem") ? new QPurchaseItem(forProperty("purchaseItem"), inits.get("purchaseItem")) : null;
        this.sharingItem = inits.isInitialized("sharingItem") ? new QSharingItem(forProperty("sharingItem"), inits.get("sharingItem")) : null;
    }

}

