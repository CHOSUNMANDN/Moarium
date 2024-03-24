package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendanceNumber is a Querydsl query type for AttendanceNumber
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceNumber extends EntityPathBase<AttendanceNumber> {

    private static final long serialVersionUID = 455835731L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendanceNumber attendanceNumber1 = new QAttendanceNumber("attendanceNumber1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DatePath<java.time.LocalDate> attendanceDate = createDate("attendanceDate", java.time.LocalDate.class);

    public final StringPath attendanceNumber = createString("attendanceNumber");

    public final NumberPath<Long> attendanceNumberId = createNumber("attendanceNumberId", Long.class);

    public final QClub club;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QAttendanceNumber(String variable) {
        this(AttendanceNumber.class, forVariable(variable), INITS);
    }

    public QAttendanceNumber(Path<? extends AttendanceNumber> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendanceNumber(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendanceNumber(PathMetadata metadata, PathInits inits) {
        this(AttendanceNumber.class, metadata, inits);
    }

    public QAttendanceNumber(Class<? extends AttendanceNumber> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
    }

}

