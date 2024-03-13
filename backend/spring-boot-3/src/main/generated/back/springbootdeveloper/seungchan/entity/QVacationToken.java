package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacationToken is a Querydsl query type for VacationToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacationToken extends EntityPathBase<VacationToken> {

    private static final long serialVersionUID = -1322639365L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacationToken vacationToken1 = new QVacationToken("vacationToken1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAttendanceState attendanceSate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final NumberPath<Integer> vacationToken = createNumber("vacationToken", Integer.class);

    public final StringPath vacationTokenDate = createString("vacationTokenDate");

    public final NumberPath<Long> vacationTokenId = createNumber("vacationTokenId", Long.class);

    public QVacationToken(String variable) {
        this(VacationToken.class, forVariable(variable), INITS);
    }

    public QVacationToken(Path<? extends VacationToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacationToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacationToken(PathMetadata metadata, PathInits inits) {
        this(VacationToken.class, metadata, inits);
    }

    public QVacationToken(Class<? extends VacationToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attendanceSate = inits.isInitialized("attendanceSate") ? new QAttendanceState(forProperty("attendanceSate"), inits.get("attendanceSate")) : null;
    }

}

