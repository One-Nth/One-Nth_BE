package com.onenth.OneNth.domain.product.entity.scrap;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharingItemScrap is a Querydsl query type for SharingItemScrap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSharingItemScrap extends EntityPathBase<SharingItemScrap> {

    private static final long serialVersionUID = -608537618L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharingItemScrap sharingItemScrap = new QSharingItemScrap("sharingItemScrap");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.onenth.OneNth.domain.member.entity.QMember member;

    public final com.onenth.OneNth.domain.product.entity.QSharingItem sharingItem;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSharingItemScrap(String variable) {
        this(SharingItemScrap.class, forVariable(variable), INITS);
    }

    public QSharingItemScrap(Path<? extends SharingItemScrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharingItemScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharingItemScrap(PathMetadata metadata, PathInits inits) {
        this(SharingItemScrap.class, metadata, inits);
    }

    public QSharingItemScrap(Class<? extends SharingItemScrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.onenth.OneNth.domain.member.entity.QMember(forProperty("member")) : null;
        this.sharingItem = inits.isInitialized("sharingItem") ? new com.onenth.OneNth.domain.product.entity.QSharingItem(forProperty("sharingItem"), inits.get("sharingItem")) : null;
    }

}

