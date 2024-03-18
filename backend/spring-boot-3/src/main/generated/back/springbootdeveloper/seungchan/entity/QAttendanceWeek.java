package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendanceWeek is a Querydsl query type for AttendanceWeek
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceWeek extends EntityPathBase<AttendanceWeek> {

    private static final long serialVersionUID = 1118044030L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendanceWeek attendanceWeek = new QAttendanceWeek("attendanceWeek");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> attendanceWeekId = createNumber("attendanceWeekId", Long.class);

    public final QClubControl clubControl;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> friday = createEnum("friday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> monday = createEnum("monday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> saturday = createEnum("saturday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> sunday = createEnum("sunday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> thursday = createEnum("thursday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> tuesday = createEnum("tuesday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS> wednesday = createEnum("wednesday", back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS.class);

    public QAttendanceWeek(String variable) {
        this(AttendanceWeek.class, forVariable(variable), INITS);
    }

    public QAttendanceWeek(Path<? extends AttendanceWeek> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendanceWeek(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendanceWeek(PathMetadata metadata, PathInits inits) {
        this(AttendanceWeek.class, metadata, inits);
    }

    public QAttendanceWeek(Class<? extends AttendanceWeek> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubControl = inits.isInitialized("clubControl") ? new QClubControl(forProperty("clubControl"), inits.get("clubControl")) : null;
    }

}

