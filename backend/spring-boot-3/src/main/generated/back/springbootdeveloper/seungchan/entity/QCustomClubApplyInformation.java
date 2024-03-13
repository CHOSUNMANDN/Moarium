package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomClubApplyInformation is a Querydsl query type for CustomClubApplyInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomClubApplyInformation extends EntityPathBase<CustomClubApplyInformation> {

    private static final long serialVersionUID = -479637946L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomClubApplyInformation customClubApplyInformation = new QCustomClubApplyInformation("customClubApplyInformation");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QClubControl clubControl;

    public final ListPath<ClubMemberCustomInformation, QClubMemberCustomInformation> clubMemberCustomInformations = this.<ClubMemberCustomInformation, QClubMemberCustomInformation>createList("clubMemberCustomInformations", ClubMemberCustomInformation.class, QClubMemberCustomInformation.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> customClubApplyInformationId = createNumber("customClubApplyInformationId", Long.class);

    public final StringPath customContent = createString("customContent");

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE> customType = createEnum("customType", back.springbootdeveloper.seungchan.constant.entity.CUSTOM_TYPE.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QCustomClubApplyInformation(String variable) {
        this(CustomClubApplyInformation.class, forVariable(variable), INITS);
    }

    public QCustomClubApplyInformation(Path<? extends CustomClubApplyInformation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomClubApplyInformation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomClubApplyInformation(PathMetadata metadata, PathInits inits) {
        this(CustomClubApplyInformation.class, metadata, inits);
    }

    public QCustomClubApplyInformation(Class<? extends CustomClubApplyInformation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubControl = inits.isInitialized("clubControl") ? new QClubControl(forProperty("clubControl"), inits.get("clubControl")) : null;
    }

}

