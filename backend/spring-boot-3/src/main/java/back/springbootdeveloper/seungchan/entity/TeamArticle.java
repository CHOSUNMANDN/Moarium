package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.TEAM_ARTICLE_SUGGESTION_CHECK;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "team_article")
@DynamicInsert // @ColumnDefault 사용 필수insert할시 Null 배제
@DynamicUpdate // update할시 Null 배재
public class TeamArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_article_id")
    private Long teamArticleId;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_article_check", length = 15)
    private TEAM_ARTICLE_SUGGESTION_CHECK answerCheck;

    @ColumnDefault(value = "0")
    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "suggestion_answer", length = 1000, nullable = false)
    private String suggestionAnswer = "";

    @Temporal(TemporalType.DATE)
    @Column(name = "team_article_date", nullable = false)
    private LocalDate TeamArticleDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teamArticle")
    private List<TeamArticleComment> teamArticleComments = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_article_classification_id")
//    private TeamArticleClassification teamArticleClassification;

    @Builder
    public TeamArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }


    @PrePersist
    protected void onCreate() {
        // https://www.daleseo.com/java8-zoned-date-time/
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
        this.TeamArticleDate = zonedDateTime.toLocalDate();

        this.answerCheck = TEAM_ARTICLE_SUGGESTION_CHECK.UNCONFIRMED;
    }


    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateAnswerCheck(TEAM_ARTICLE_SUGGESTION_CHECK check) {
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

    public void addTeamArticleComment(final TeamArticleComment teamArticleComment) {
        this.teamArticleComments.add(teamArticleComment);
        if (teamArticleComment.getTeamArticle() != this) { // 무한루프에 빠지지 않도록 체크
            teamArticleComment.setTeamArticle(this);
        }
    }

    public List<TeamArticleComment> getTeamArticleComments() {
        return teamArticleComments;
    }

//    public TeamArticleClassification getTeamArticleClassification() {
//        return teamArticleClassification;
//    }

//    public void setTeamArticleClassification(final TeamArticleClassification teamArticleClassification) {
//        this.teamArticleClassification = teamArticleClassification;
//        // 무한루프에 빠지지 않도록 체크
//        if (!teamArticleClassification.getTeamArticles().contains(this)) {
//            teamArticleClassification.getTeamArticles().add(this);
//        }
//    }
}
