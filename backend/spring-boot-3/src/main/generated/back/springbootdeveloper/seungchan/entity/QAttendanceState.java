package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendanceState is a Querydsl query type for AttendanceState
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceState extends EntityPathBase<AttendanceState> {

    private static final long serialVersionUID = 296375879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendanceState attendanceState = new QAttendanceState("attendanceState");

    public final QAttendanceCheckTime attendanceCheckTime;

    public final NumberPath<Long> attendanceStateId = createNumber("attendanceStateId", Long.class);

    public final ListPath<AttendanceWeekDate, QAttendanceWeekDate> attendanceWeekDates = this.<AttendanceWeekDate, QAttendanceWeekDate>createList("attendanceWeekDates", AttendanceWeekDate.class, QAttendanceWeekDate.class, PathInits.DIRECT2);

    public final ListPath<VacationToken, QVacationToken> vacationTokens = this.<VacationToken, QVacationToken>createList("vacationTokens", VacationToken.class, QVacationToken.class, PathInits.DIRECT2);

    public QAttendanceState(String variable) {
        this(AttendanceState.class, forVariable(variable), INITS);
    }

    public QAttendanceState(Path<? extends AttendanceState> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendanceState(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendanceState(PathMetadata metadata, PathInits inits) {
        this(AttendanceState.class, metadata, inits);
    }

    public QAttendanceState(Class<? extends AttendanceState> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attendanceCheckTime = inits.isInitialized("attendanceCheckTime") ? new QAttendanceCheckTime(forProperty("attendanceCheckTime"), inits.get("attendanceCheckTime")) : null;
    }

}

