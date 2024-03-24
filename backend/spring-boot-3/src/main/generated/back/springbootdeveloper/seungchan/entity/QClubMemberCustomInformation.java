package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMemberCustomInformation is a Querydsl query type for ClubMemberCustomInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMemberCustomInformation extends EntityPathBase<ClubMemberCustomInformation> {

    private static final long serialVersionUID = -494258038L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubMemberCustomInformation clubMemberCustomInformation = new QClubMemberCustomInformation("clubMemberCustomInformation");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> clubMemberCustomInformationId = createNumber("clubMemberCustomInformationId", Long.class);

    public final QClubMemberInformation clubMemberInformation;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final QCustomClubApplyInformation customClubApplyInformation;

    public final StringPath customContent = createString("customContent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClubMemberCustomInformation(String variable) {
        this(ClubMemberCustomInformation.class, forVariable(variable), INITS);
    }

    public QClubMemberCustomInformation(Path<? extends ClubMemberCustomInformation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubMemberCustomInformation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubMemberCustomInformation(PathMetadata metadata, PathInits inits) {
        this(ClubMemberCustomInformation.class, metadata, inits);
    }

    public QClubMemberCustomInformation(Class<? extends ClubMemberCustomInformation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubMemberInformation = inits.isInitialized("clubMemberInformation") ? new QClubMemberInformation(forProperty("clubMemberInformation")) : null;
        this.customClubApplyInformation = inits.isInitialized("customClubApplyInformation") ? new QCustomClubApplyInformation(forProperty("customClubApplyInformation"), inits.get("customClubApplyInformation")) : null;
    }

}

