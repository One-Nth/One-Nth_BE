package com.onenth.OneNth.domain.deal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDealConfirmation is a Querydsl query type for DealConfirmation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDealConfirmation extends EntityPathBase<DealConfirmation> {

    private static final long serialVersionUID = 680886279L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDealConfirmation dealConfirmation = new QDealConfirmation("dealConfirmation");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final com.onenth.OneNth.domain.member.entity.QMember buyer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QDealCompletion dealCompletion;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.onenth.OneNth.domain.product.entity.enums.ItemType> itemType = createEnum("itemType", com.onenth.OneNth.domain.product.entity.enums.ItemType.class);

    public final NumberPath<Integer> originalPrice = createNumber("originalPrice", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final com.onenth.OneNth.domain.member.entity.QMember seller;

    public final DatePath<java.time.LocalDate> tradeDate = createDate("tradeDate", java.time.LocalDate.class);

    public final NumberPath<Integer> tradePrice = createNumber("tradePrice", Integer.class);

    public final EnumPath<com.onenth.OneNth.domain.deal.entity.enums.TradeType> tradeType = createEnum("tradeType", com.onenth.OneNth.domain.deal.entity.enums.TradeType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDealConfirmation(String variable) {
        this(DealConfirmation.class, forVariable(variable), INITS);
    }

    public QDealConfirmation(Path<? extends DealConfirmation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDealConfirmation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDealConfirmation(PathMetadata metadata, PathInits inits) {
        this(DealConfirmation.class, metadata, inits);
    }

    public QDealConfirmation(Class<? extends DealConfirmation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("buyer")) : null;
        this.dealCompletion = inits.isInitialized("dealCompletion") ? new QDealCompletion(forProperty("dealCompletion"), inits.get("dealCompletion")) : null;
        this.seller = inits.isInitialized("seller") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("seller")) : null;
    }

}

