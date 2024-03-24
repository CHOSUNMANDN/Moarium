package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = -1858574601L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClub club = new QClub("club");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<AttendanceNumber, QAttendanceNumber> attendanceNumbers = this.<AttendanceNumber, QAttendanceNumber>createList("attendanceNumbers", AttendanceNumber.class, QAttendanceNumber.class, PathInits.DIRECT2);

    public final QClubControl clubControl;

    public final NumberPath<Long> clubId = createNumber("clubId", Long.class);

    public final StringPath clubIntroduce = createString("clubIntroduce");

    public final ListPath<ClubIntroduceImage, QClubIntroduceImage> clubIntroduceImages = this.<ClubIntroduceImage, QClubIntroduceImage>createList("clubIntroduceImages", ClubIntroduceImage.class, QClubIntroduceImage.class, PathInits.DIRECT2);

    public final StringPath clubName = createString("clubName");

    public final StringPath clubProfileImage = createString("clubProfileImage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClub(String variable) {
        this(Club.class, forVariable(variable), INITS);
    }

    public QClub(Path<? extends Club> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClub(PathMetadata metadata, PathInits inits) {
        this(Club.class, metadata, inits);
    }

    public QClub(Class<? extends Club> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubControl = inits.isInitialized("clubControl") ? new QClubControl(forProperty("clubControl"), inits.get("clubControl")) : null;
    }

}

