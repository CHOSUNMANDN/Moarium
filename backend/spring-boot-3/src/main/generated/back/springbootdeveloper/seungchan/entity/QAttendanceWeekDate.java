package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendanceWeekDate is a Querydsl query type for AttendanceWeekDate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceWeekDate extends EntityPathBase<AttendanceWeekDate> {

    private static final long serialVersionUID = 1234990156L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendanceWeekDate attendanceWeekDate = new QAttendanceWeekDate("attendanceWeekDate");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAttendanceState attendanceState;

    public final NumberPath<Long> attendanceWeekDateId = createNumber("attendanceWeekDateId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> friday = createEnum("friday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> monday = createEnum("monday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    public final DatePath<java.time.LocalDate> mondayDate = createDate("mondayDate", java.time.LocalDate.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> saturday = createEnum("saturday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> sunday = createEnum("sunday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    public final DatePath<java.time.LocalDate> sundayDate = createDate("sundayDate", java.time.LocalDate.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> thursday = createEnum("thursday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> tuesday = createEnum("tuesday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE> wednesday = createEnum("wednesday", back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE.class);

    public QAttendanceWeekDate(String variable) {
        this(AttendanceWeekDate.class, forVariable(variable), INITS);
    }

    public QAttendanceWeekDate(Path<? extends AttendanceWeekDate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendanceWeekDate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendanceWeekDate(PathMetadata metadata, PathInits inits) {
        this(AttendanceWeekDate.class, metadata, inits);
    }

    public QAttendanceWeekDate(Class<? extends AttendanceWeekDate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attendanceState = inits.isInitialized("attendanceState") ? new QAttendanceState(forProperty("attendanceState"), inits.get("attendanceState")) : null;
    }

}

