package com.onenth.OneNth.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailVerificationCode is a Querydsl query type for EmailVerificationCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailVerificationCode extends EntityPathBase<EmailVerificationCode> {

    private static final long serialVersionUID = -548057936L;

    public static final QEmailVerificationCode emailVerificationCode = new QEmailVerificationCode("emailVerificationCode");

    public final com.onenth.OneNth.domain.common.QBaseEntity _super = new com.onenth.OneNth.domain.common.QBaseEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isVerified = createBoolean("isVerified");

    public final StringPath purpose = createString("purpose");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEmailVerificationCode(String variable) {
        super(EmailVerificationCode.class, forVariable(variable));
    }

    public QEmailVerificationCode(Path<? extends EmailVerificationCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailVerificationCode(PathMetadata metadata) {
        super(EmailVerificationCode.class, metadata);
    }

}

