package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleComment is a Querydsl query type for ArticleComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleComment extends EntityPathBase<ArticleComment> {

    private static final long serialVersionUID = -1376125558L;

    public static final QArticleComment articleComment = new QArticleComment("articleComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> articleCommentId = createNumber("articleCommentId", Long.class);

    public final DatePath<java.time.LocalDate> commentDate = createDate("commentDate", java.time.LocalDate.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> declarationCount = createNumber("declarationCount", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QArticleComment(String variable) {
        super(ArticleComment.class, forVariable(variable));
    }

    public QArticleComment(Path<? extends ArticleComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleComment(PathMetadata metadata) {
        super(ArticleComment.class, metadata);
    }

}

