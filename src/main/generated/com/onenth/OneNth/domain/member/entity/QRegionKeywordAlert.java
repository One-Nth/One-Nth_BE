package com.onenth.OneNth.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegionKeywordAlert is a Querydsl query type for RegionKeywordAlert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegionKeywordAlert extends EntityPathBase<RegionKeywordAlert> {

    private static final long serialVersionUID = -153914565L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegionKeywordAlert regionKeywordAlert = new QRegionKeywordAlert("regionKeywordAlert");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final com.onenth.OneNth.domain.region.entity.QRegion regionKeyword;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRegionKeywordAlert(String variable) {
        this(RegionKeywordAlert.class, forVariable(variable), INITS);
    }

    public QRegionKeywordAlert(Path<? extends RegionKeywordAlert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegionKeywordAlert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegionKeywordAlert(PathMetadata metadata, PathInits inits) {
        this(RegionKeywordAlert.class, metadata, inits);
    }

    public QRegionKeywordAlert(Class<? extends RegionKeywordAlert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.regionKeyword = inits.isInitialized("regionKeyword") ? new com.onenth.OneNth.domain.region.entity.QRegion(forProperty("regionKeyword")) : null;
    }

}

