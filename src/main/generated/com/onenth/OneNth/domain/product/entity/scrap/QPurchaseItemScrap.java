package com.onenth.OneNth.domain.product.entity.scrap;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseItemScrap is a Querydsl query type for PurchaseItemScrap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseItemScrap extends EntityPathBase<PurchaseItemScrap> {

    private static final long serialVersionUID = -322872047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseItemScrap purchaseItemScrap = new QPurchaseItemScrap("purchaseItemScrap");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final com.onenth.OneNth.domain.product.entity.QPurchaseItem purchaseItem;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPurchaseItemScrap(String variable) {
        this(PurchaseItemScrap.class, forVariable(variable), INITS);
    }

    public QPurchaseItemScrap(Path<? extends PurchaseItemScrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseItemScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseItemScrap(PathMetadata metadata, PathInits inits) {
        this(PurchaseItemScrap.class, metadata, inits);
    }

    public QPurchaseItemScrap(Class<? extends PurchaseItemScrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.purchaseItem = inits.isInitialized("purchaseItem") ? new com.onenth.OneNth.domain.product.entity.QPurchaseItem(forProperty("purchaseItem"), inits.get("purchaseItem")) : null;
    }

}

