package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefreshToken is a Querydsl query type for RefreshToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshToken extends EntityPathBase<RefreshToken> {

    private static final long serialVersionUID = -165037793L;

    public static final QRefreshToken refreshToken1 = new QRefreshToken("refreshToken1");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> refresh_token_id = createNumber("refresh_token_id", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QRefreshToken(String variable) {
        super(RefreshToken.class, forVariable(variable));
    }

    public QRefreshToken(Path<? extends RefreshToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefreshToken(PathMetadata metadata) {
        super(RefreshToken.class, metadata);
    }

}

