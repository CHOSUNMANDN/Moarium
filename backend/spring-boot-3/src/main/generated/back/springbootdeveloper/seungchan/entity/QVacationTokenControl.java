package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacationTokenControl is a Querydsl query type for VacationTokenControl
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacationTokenControl extends EntityPathBase<VacationTokenControl> {

    private static final long serialVersionUID = 1169947874L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacationTokenControl vacationTokenControl1 = new QVacationTokenControl("vacationTokenControl1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QClubControl clubControl;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final NumberPath<Integer> vacationTokenControl = createNumber("vacationTokenControl", Integer.class);

    public final NumberPath<Long> vacationTokenControlId = createNumber("vacationTokenControlId", Long.class);

    public QVacationTokenControl(String variable) {
        this(VacationTokenControl.class, forVariable(variable), INITS);
    }

    public QVacationTokenControl(Path<? extends VacationTokenControl> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacationTokenControl(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacationTokenControl(PathMetadata metadata, PathInits inits) {
        this(VacationTokenControl.class, metadata, inits);
    }

    public QVacationTokenControl(Class<? extends VacationTokenControl> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubControl = inits.isInitialized("clubControl") ? new QClubControl(forProperty("clubControl"), inits.get("clubControl")) : null;
    }

}

