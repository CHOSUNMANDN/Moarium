package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.ANONYMITY;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_article_comment")
public class ClubArticleComment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_article_comment_id")
  private Long clubArticleCommentId;

  @Column(name = "content", length = 255, nullable = false)
  private String content;

  @Temporal(TemporalType.DATE)
  @Column(name = "comment_date", nullable = false)
  private LocalDate commentDate;

  @Column(name = "like_count")
  private Integer likeCount = 0;

  @Column(name = "declaration_count")
  private Integer declarationCount = 0;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Enumerated(EnumType.STRING)
  @Column(name = "anonymous", length = 15, nullable = false)
  private ANONYMITY anonymity = ANONYMITY.REAL_NAME;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "club_article_id")
  private ClubArticle clubArticle;

  @Builder
  public ClubArticleComment(String content, Long memberId, ANONYMITY anonymity) {
    this.content = content;
    this.memberId = memberId;
    this.anonymity = anonymity;
  }

  @PrePersist
  protected void onCreate() {
    // https://www.daleseo.com/java8-zoned-date-time/
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.commentDate = zonedDateTime.toLocalDate();
  }

  /**
   * 내용을 업데이트하는 메서드입니다.
   *
   * @param content 새로운 내용
   */
  public void updateContent(String content) {
    this.content = content;
  }

  /**
   * 익명성을 업데이트하는 메서드입니다.
   *
   * @param anonymity 익명성
   */
  public void updateAnonymity(ANONYMITY anonymity) {
    this.anonymity = anonymity;
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
   * 댓글 작성 날짜를 문자열로 반환하는 메서드입니다.
   *
   * @return 댓글 작성 날짜 문자열
   */
  public String getCommentDate() {
    return this.commentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  /**
   * 클럽 글을 반환하는 메서드입니다.
   *
   * @return 클럽 글
   */
  public ClubArticle getClubArticle() {
    return clubArticle;
  }

  /**
   * 클럽 글을 설정하는 메서드입니다. 동시에 무한 루프를 방지하기 위해 해당 댓글이 클럽 글에 이미 추가되어 있는지 확인합니다.
   *
   * @param clubArticle 설정할 클럽 글
   */
  public void setClubArticle(final ClubArticle clubArticle) {
    this.clubArticle = clubArticle;
    // 무한루프에 빠지지 않도록 체크
    if (!clubArticle.getClubArticleComments().contains(this)) {
      clubArticle.getClubArticleComments().add(this);
    }
  }

}
