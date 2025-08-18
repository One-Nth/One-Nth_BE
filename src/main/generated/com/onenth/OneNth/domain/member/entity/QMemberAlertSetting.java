package com.onenth.OneNth.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAlertSetting is a Querydsl query type for MemberAlertSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAlertSetting extends EntityPathBase<MemberAlertSetting> {

    private static final long serialVersionUID = 1242621506L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAlertSetting memberAlertSetting = new QMemberAlertSetting("memberAlertSetting");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final BooleanPath chatAlerts = createBoolean("chatAlerts");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final BooleanPath reviewAlerts = createBoolean("reviewAlerts");

    public final BooleanPath scrapAlerts = createBoolean("scrapAlerts");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberAlertSetting(String variable) {
        this(MemberAlertSetting.class, forVariable(variable), INITS);
    }

    public QMemberAlertSetting(Path<? extends MemberAlertSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAlertSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAlertSetting(PathMetadata metadata, PathInits inits) {
        this(MemberAlertSetting.class, metadata, inits);
    }

    public QMemberAlertSetting(Class<? extends MemberAlertSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

