package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMemberInformation is a Querydsl query type for ClubMemberInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMemberInformation extends EntityPathBase<ClubMemberInformation> {

    private static final long serialVersionUID = 690394107L;

    public static final QClubMemberInformation clubMemberInformation = new QClubMemberInformation("clubMemberInformation");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<ClubMemberCustomInformation, QClubMemberCustomInformation> clubMemberCustomInformations = this.<ClubMemberCustomInformation, QClubMemberCustomInformation>createList("clubMemberCustomInformations", ClubMemberCustomInformation.class, QClubMemberCustomInformation.class, PathInits.DIRECT2);

    public final NumberPath<Long> clubMemberInformationId = createNumber("clubMemberInformationId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK> favoriteCheck = createEnum("favoriteCheck", back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK.class);

    public final StringPath introduce = createString("introduce");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClubMemberInformation(String variable) {
        super(ClubMemberInformation.class, forVariable(variable));
    }

    public QClubMemberInformation(Path<? extends ClubMemberInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubMemberInformation(PathMetadata metadata) {
        super(ClubMemberInformation.class, metadata);
    }

}

