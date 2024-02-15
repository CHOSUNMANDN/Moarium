package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_SUGGESTION_CHECK;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
public class ClubArticle {
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

    @Temporal(TemporalType.DATE)
    @Column(name = "club_article_date", nullable = false)
    private LocalDate ClubArticleDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clubArticle")
    private List<ClubArticleComment> clubArticleComments = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "club_article_classification_id")
//    private ClubArticleClassification clubArticleClassification;

    @Builder
    public ClubArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }


    @PrePersist
    protected void onCreate() {
        // https://www.daleseo.com/java8-zoned-date-time/
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
        this.ClubArticleDate = zonedDateTime.toLocalDate();

        this.answerCheck = CLUB_ARTICLE_SUGGESTION_CHECK.UNCONFIRMED;
    }


    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateAnswerCheck(CLUB_ARTICLE_SUGGESTION_CHECK check) {
        this.answerCheck = check;
    }

    public void subtractLike() {
        if (this.likeCount > 0) {
            this.likeCount = this.likeCount - 1;
        }
    }

    public void addLike() {
        this.likeCount = this.likeCount + 1;
    }

    public void updateSuggestionAnswer(String suggestionAnswer) {
        this.suggestionAnswer = suggestionAnswer;
    }

    public void addClubArticleComment(final ClubArticleComment clubArticleComment) {
        this.clubArticleComments.add(clubArticleComment);
        if (clubArticleComment.getClubArticle() != this) { // 무한루프에 빠지지 않도록 체크
            clubArticleComment.setClubArticle(this);
        }
    }

    public List<ClubArticleComment> getClubArticleComments() {
        return clubArticleComments;
    }

//    public ClubArticleClassification getClubArticleClassification() {
//        return clubArticleClassification;
//    }
//
//    public void setClubArticleClassification(final ClubArticleClassification clubArticleClassification) {
//        this.clubArticleClassification = clubArticleClassification;
//        // 무한루프에 빠지지 않도록 체크
//        if (!clubArticleClassification.getclubArticles().contains(this)) {
//            clubArticleClassification.getclubArticles().add(this);
//        }
//    }
}