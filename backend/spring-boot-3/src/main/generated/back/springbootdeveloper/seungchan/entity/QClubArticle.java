package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubArticle is a Querydsl query type for ClubArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubArticle extends EntityPathBase<ClubArticle> {

    private static final long serialVersionUID = 1232898559L;

    public static final QClubArticle clubArticle = new QClubArticle("clubArticle");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ANONYMITY> anonymity = createEnum("anonymity", back.springbootdeveloper.seungchan.constant.entity.ANONYMITY.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_SUGGESTION_CHECK> answerCheck = createEnum("answerCheck", back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_SUGGESTION_CHECK.class);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION> classification = createEnum("classification", back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION.class);

    public final ListPath<ClubArticleComment, QClubArticleComment> clubArticleComments = this.<ClubArticleComment, QClubArticleComment>createList("clubArticleComments", ClubArticleComment.class, QClubArticleComment.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> ClubArticleDate = createDate("ClubArticleDate", java.time.LocalDate.class);

    public final NumberPath<Long> clubArticleId = createNumber("clubArticleId", Long.class);

    public final NumberPath<Long> clubId = createNumber("clubId", Long.class);

    public final NumberPath<Long> clubMemberId = createNumber("clubMemberId", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath suggestionAnswer = createString("suggestionAnswer");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QClubArticle(String variable) {
        super(ClubArticle.class, forVariable(variable));
    }

    public QClubArticle(Path<? extends ClubArticle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubArticle(PathMetadata metadata) {
        super(ClubArticle.class, metadata);
    }

}

