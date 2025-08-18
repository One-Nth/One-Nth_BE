package com.onenth.OneNth.domain.deal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCancelledDeal is a Querydsl query type for CancelledDeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCancelledDeal extends EntityPathBase<CancelledDeal> {

    private static final long serialVersionUID = -562095657L;

    public static final QCancelledDeal cancelledDeal = new QCancelledDeal("cancelledDeal");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final EnumPath<com.onenth.OneNth.domain.deal.entity.enums.CancelReason> cancelReason = createEnum("cancelReason", com.onenth.OneNth.domain.deal.entity.enums.CancelReason.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.ItemType> itemType = createEnum("itemType", com.onenth.OneNth.domain.product.entity.enums.ItemType.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCancelledDeal(String variable) {
        super(CancelledDeal.class, forVariable(variable));
    }

    public QCancelledDeal(Path<? extends CancelledDeal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCancelledDeal(PathMetadata metadata) {
        super(CancelledDeal.class, metadata);
    }

}

