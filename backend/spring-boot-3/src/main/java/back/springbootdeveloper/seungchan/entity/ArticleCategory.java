package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.ARTICLE_CATEGORY;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "article_category")
public class ArticleCategory extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_category_id")
  private Long articleCategoryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "article_category", length = 15, nullable = false)
  private ARTICLE_CATEGORY articleCategory;

  @Builder
  public ArticleCategory(ARTICLE_CATEGORY articleCategory) {
    this.articleCategory = articleCategory;
  }

  public void updateArticleCategory(ARTICLE_CATEGORY articleCategory) {
    this.articleCategory = articleCategory;
  }
}
