package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.ANONYMITY;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_SUGGESTION_CHECK;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_article")
public class ClubArticle extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_article_id")
  private Long clubArticleId;

  @Column(name = "title", length = 255, nullable = false)
  private String title;

  @Column(name = "content", length = 1000, nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "club_article_check", length = 15)
  private CLUB_ARTICLE_SUGGESTION_CHECK answerCheck;

  @Column(name = "like_count")
  private Integer likeCount = 0;

  @Column(name = "suggestion_answer", length = 1000, nullable = false)
  private String suggestionAnswer = "";

  @Enumerated(EnumType.STRING)
  @Column(name = "classification", length = 15, nullable = false)
  private CLUB_ARTICLE_CLASSIFICATION classification;

  @Temporal(TemporalType.DATE)
  @Column(name = "club_article_date", nullable = false)
  private LocalDate ClubArticleDate;


  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clubArticle")
  private List<ClubArticleComment> clubArticleComments = new ArrayList<>();

  @Column(name = "club_member_id", nullable = false)
  private Long clubMemberId;

  @Enumerated(EnumType.STRING)
  @Column(name = "anonymous", length = 15, nullable = false)
  private ANONYMITY anonymity = ANONYMITY.REAL_NAME;

  @Builder
  public ClubArticle(String title, String content, CLUB_ARTICLE_CLASSIFICATION classification,
      Long clubMemberId, ANONYMITY anonymity) {
    this.title = title;
    this.content = content;
    this.classification = classification;
    this.clubMemberId = clubMemberId;
    this.anonymity = anonymity;
  }


  @PrePersist
  protected void onCreate() {
    // https://www.daleseo.com/java8-zoned-date-time/
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.ClubArticleDate = zonedDateTime.toLocalDate();

    this.answerCheck = CLUB_ARTICLE_SUGGESTION_CHECK.UNCONFIRMED;
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
   * 클럽 글에 대한 제안 확인을 업데이트하는 메서드입니다.
   *
   * @param check 제안 확인
   */
  public void updateAnswerCheck(CLUB_ARTICLE_SUGGESTION_CHECK check) {
    this.answerCheck = check;
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
   * 제안 답변을 업데이트하는 메서드입니다.
   *
   * @param suggestionAnswer 제안 답변
   */
  public void updateSuggestionAnswer(String suggestionAnswer) {
    this.suggestionAnswer = suggestionAnswer;
  }

  /**
   * 클럽 글에 댓글을 추가하는 메서드입니다.
   *
   * @param clubArticleComment 추가할 클럽 글 댓글
   */
  public void addClubArticleComment(final ClubArticleComment clubArticleComment) {
    this.clubArticleComments.add(clubArticleComment);
    if (clubArticleComment.getClubArticle() != this) { // 무한루프에 빠지지 않도록 체크
      clubArticleComment.setClubArticle(this);
    }
  }

  /**
   * 클럽 글의 작성 날짜를 문자열로 반환하는 메서드입니다.
   *
   * @return 클럽 글 작성 날짜 문자열
   */
  public String getClubArticleDate() {
    return String.valueOf(ClubArticleDate);
  }

}
