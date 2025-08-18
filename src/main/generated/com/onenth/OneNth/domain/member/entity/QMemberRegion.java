package com.onenth.OneNth.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberRegion is a Querydsl query type for MemberRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRegion extends EntityPathBase<MemberRegion> {

    private static final long serialVersionUID = -1329087902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberRegion memberRegion = new QMemberRegion("memberRegion");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMain = createBoolean("isMain");

    public final BooleanPath isVerified = createBoolean("isVerified");

    public final QMember member;

    public final com.onenth.OneNth.domain.region.entity.QRegion region;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberRegion(String variable) {
        this(MemberRegion.class, forVariable(variable), INITS);
    }

    public QMemberRegion(Path<? extends MemberRegion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberRegion(PathMetadata metadata, PathInits inits) {
        this(MemberRegion.class, metadata, inits);
    }

    public QMemberRegion(Class<? extends MemberRegion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.region = inits.isInitialized("region") ? new com.onenth.OneNth.domain.region.entity.QRegion(forProperty("region")) : null;
    }

}

