package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id")
  private Long articleId;

  @Column(name = "title", length = 255, nullable = false)
  private String title;

  @Column(name = "content", length = 1000, nullable = false)
  private String content;

  @Temporal(TemporalType.DATE)
  @Column(name = "article_date", nullable = false)
  private LocalDate articleDate;

  @Column(name = "declaration_count")
  private Integer declarationCount = 0;

  @Column(name = "like_count")
  private Integer likeCount = 0;

  @Builder
  public Article(String title, String content) {
    this.title = title;
    this.content = content;
  }

  @PrePersist
  protected void onCreate() {
    // https://www.daleseo.com/java8-zoned-date-time/
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.articleDate = zonedDateTime.toLocalDate();
  }

  /**
   * 글의 제목을 업데이트하는 메서드입니다.
   *
   * @param title 새로운 제목
   */
  public void updateTitle(String title) {
    this.title = title;
  }

  /**
   * 글의 내용을 업데이트하는 메서드입니다.
   *
   * @param content 새로운 내용
   */
  public void updateContent(String content) {
    this.content = content;
  }

  /**
   * 신고 수를 감소시키는 메서드입니다. 만약 신고 수가 0보다 큰 경우에만 감소시킵니다.
   */
  public void subtractDeclaration() {
    if (this.declarationCount > 0) {
      this.declarationCount = this.declarationCount - 1;
    }
  }

  /**
   * 신고 수를 증가시키는 메서드입니다.
   */
  public void addDeclaration() {
    this.declarationCount = this.declarationCount + 1;
  }

  /**
   * 좋아요 수를 감소시키는 메서드입니다. 만약 좋아요 수가 0보다 큰 경우에만 감소시킵니다.
   */
  public void subtractLike() {
    if (this.likeCount > 0) {
      this.likeCount = this.likeCount - 1;
    }
  }

  /**
   * 좋아요 수를 증가시키는 메서드입니다.
   */
  public void addLike() {
    this.likeCount = this.likeCount + 1;
  }
}
