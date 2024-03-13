package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubArticleComment is a Querydsl query type for ClubArticleComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubArticleComment extends EntityPathBase<ClubArticleComment> {

    private static final long serialVersionUID = -382628000L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubArticleComment clubArticleComment = new QClubArticleComment("clubArticleComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ANONYMITY> anonymity = createEnum("anonymity", back.springbootdeveloper.seungchan.constant.entity.ANONYMITY.class);

    public final QClubArticle clubArticle;

    public final NumberPath<Long> clubArticleCommentId = createNumber("clubArticleCommentId", Long.class);

    public final DatePath<java.time.LocalDate> commentDate = createDate("commentDate", java.time.LocalDate.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> declarationCount = createNumber("declarationCount", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClubArticleComment(String variable) {
        this(ClubArticleComment.class, forVariable(variable), INITS);
    }

    public QClubArticleComment(Path<? extends ClubArticleComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubArticleComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubArticleComment(PathMetadata metadata, PathInits inits) {
        this(ClubArticleComment.class, metadata, inits);
    }

    public QClubArticleComment(Class<? extends ClubArticleComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubArticle = inits.isInitialized("clubArticle") ? new QClubArticle(forProperty("clubArticle")) : null;
    }

}

