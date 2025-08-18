package com.onenth.OneNth.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductKeywordAlert is a Querydsl query type for ProductKeywordAlert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductKeywordAlert extends EntityPathBase<ProductKeywordAlert> {

    private static final long serialVersionUID = 31268046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductKeywordAlert productKeywordAlert = new QProductKeywordAlert("productKeywordAlert");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductKeywordAlert(String variable) {
        this(ProductKeywordAlert.class, forVariable(variable), INITS);
    }

    public QProductKeywordAlert(Path<? extends ProductKeywordAlert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductKeywordAlert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductKeywordAlert(PathMetadata metadata, PathInits inits) {
        this(ProductKeywordAlert.class, metadata, inits);
    }

    public QProductKeywordAlert(Class<? extends ProductKeywordAlert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

