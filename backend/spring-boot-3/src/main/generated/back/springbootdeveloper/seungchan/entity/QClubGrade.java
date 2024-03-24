package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubGrade is a Querydsl query type for ClubGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubGrade extends EntityPathBase<ClubGrade> {

    private static final long serialVersionUID = 699542304L;

    public static final QClubGrade clubGrade1 = new QClubGrade("clubGrade1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE> clubGrade = createEnum("clubGrade", back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE.class);

    public final NumberPath<Long> clubGradeId = createNumber("clubGradeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClubGrade(String variable) {
        super(ClubGrade.class, forVariable(variable));
    }

    public QClubGrade(Path<? extends ClubGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubGrade(PathMetadata metadata) {
        super(ClubGrade.class, metadata);
    }

}

