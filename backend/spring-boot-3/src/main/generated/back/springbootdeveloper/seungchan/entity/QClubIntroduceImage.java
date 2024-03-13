package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubIntroduceImage is a Querydsl query type for ClubIntroduceImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubIntroduceImage extends EntityPathBase<ClubIntroduceImage> {

    private static final long serialVersionUID = 1396326995L;

    public static final QClubIntroduceImage clubIntroduceImage = new QClubIntroduceImage("clubIntroduceImage");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> clubIntroduceImageId = createNumber("clubIntroduceImageId", Long.class);

    public final StringPath clubIntroduceImageUrl = createString("clubIntroduceImageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClubIntroduceImage(String variable) {
        super(ClubIntroduceImage.class, forVariable(variable));
    }

    public QClubIntroduceImage(Path<? extends ClubIntroduceImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubIntroduceImage(PathMetadata metadata) {
        super(ClubIntroduceImage.class, metadata);
    }

}

