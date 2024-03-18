package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubControl is a Querydsl query type for ClubControl
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubControl extends EntityPathBase<ClubControl> {

    private static final long serialVersionUID = -1378147738L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubControl clubControl = new QClubControl("clubControl");

    public final QAttendanceWeek attendanceWeek;

    public final QClub club;

    public final NumberPath<Long> clubControlId = createNumber("clubControlId", Long.class);

    public final ListPath<CustomClubApplyInformation, QCustomClubApplyInformation> customClubApplyInformations = this.<CustomClubApplyInformation, QCustomClubApplyInformation>createList("customClubApplyInformations", CustomClubApplyInformation.class, QCustomClubApplyInformation.class, PathInits.DIRECT2);

    public final QVacationTokenControl vacationTokenControl;

    public QClubControl(String variable) {
        this(ClubControl.class, forVariable(variable), INITS);
    }

    public QClubControl(Path<? extends ClubControl> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubControl(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubControl(PathMetadata metadata, PathInits inits) {
        this(ClubControl.class, metadata, inits);
    }

    public QClubControl(Class<? extends ClubControl> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attendanceWeek = inits.isInitialized("attendanceWeek") ? new QAttendanceWeek(forProperty("attendanceWeek"), inits.get("attendanceWeek")) : null;
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.vacationTokenControl = inits.isInitialized("vacationTokenControl") ? new QVacationTokenControl(forProperty("vacationTokenControl"), inits.get("vacationTokenControl")) : null;
    }

}

