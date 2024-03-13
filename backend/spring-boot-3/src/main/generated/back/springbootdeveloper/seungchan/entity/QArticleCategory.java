package back.springbootdeveloper.seungchan.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleCategory is a Querydsl query type for ArticleCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleCategory extends EntityPathBase<ArticleCategory> {

    private static final long serialVersionUID = 942707507L;

    public static final QArticleCategory articleCategory1 = new QArticleCategory("articleCategory1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<back.springbootdeveloper.seungchan.constant.entity.ARTICLE_CATEGORY> articleCategory = createEnum("articleCategory", back.springbootdeveloper.seungchan.constant.entity.ARTICLE_CATEGORY.class);

    public final NumberPath<Long> articleCategoryId = createNumber("articleCategoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QArticleCategory(String variable) {
        super(ArticleCategory.class, forVariable(variable));
    }

    public QArticleCategory(Path<? extends ArticleCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleCategory(PathMetadata metadata) {
        super(ArticleCategory.class, metadata);
    }

}

