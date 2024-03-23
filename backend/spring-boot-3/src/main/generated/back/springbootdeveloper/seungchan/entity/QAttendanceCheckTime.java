package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendanceCheckTime is a Querydsl query type for AttendanceCheckTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceCheckTime extends EntityPathBase<AttendanceCheckTime> {

    private static final long serialVersionUID = -2135576917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendanceCheckTime attendanceCheckTime = new QAttendanceCheckTime("attendanceCheckTime");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAttendanceState attendanceSate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> friday = createEnum("friday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.LONG_VACATION> longVacation = createEnum("longVacation", back.springbootdeveloper.seungchan.constant.entity.LONG_VACATION.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> monday = createEnum("monday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> saturday = createEnum("saturday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> sunday = createEnum("sunday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> thursday = createEnum("thursday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> tuesday = createEnum("tuesday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME> wednesday = createEnum("wednesday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME.class);

    public QAttendanceCheckTime(String variable) {
        this(AttendanceCheckTime.class, forVariable(variable), INITS);
    }

    public QAttendanceCheckTime(Path<? extends AttendanceCheckTime> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendanceCheckTime(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendanceCheckTime(PathMetadata metadata, PathInits inits) {
        this(AttendanceCheckTime.class, metadata, inits);
    }

    public QAttendanceCheckTime(Class<? extends AttendanceCheckTime> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attendanceSate = inits.isInitialized("attendanceSate") ? new QAttendanceState(forProperty("attendanceSate"), inits.get("attendanceSate")) : null;
    }

}

