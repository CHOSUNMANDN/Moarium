package back.springbootdeveloper.seungchan.entity;

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
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "team_article_comment")
@DynamicInsert // @ColumnDefault 사용 필수insert할시 Null 배제
@DynamicUpdate // update할시 Null 배재
public class TeamArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_article_comment_id")
    private Long teamArticleCommentId;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(name = "comment_date", nullable = false)
    private LocalDate commentDate;

    @ColumnDefault(value = "0")
    @Column(name = "like_count")
    private Integer likeCount;

    @ColumnDefault(value = "0")
    @Column(name = "declaration_count")
    private Integer declarationCount;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_article_id")
    private TeamArticle teamArticle;

    @Builder
    public TeamArticleComment(String content) {
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        // https://www.daleseo.com/java8-zoned-date-time/
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
        this.commentDate = zonedDateTime.toLocalDate();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void subtractLike() {
        if (this.likeCount > 0) {
            this.likeCount = this.likeCount - 1;
        }
    }

    public void addLike() {
        this.likeCount = this.likeCount + 1;
    }

    public void subtractDeclaration() {
        if (this.declarationCount > 0) {
            this.declarationCount = this.declarationCount - 1;
        }
    }

    public void addDeclaration() {
        this.declarationCount = this.declarationCount + 1;
    }

    public String getCommentDate() {
        return this.commentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    public TeamArticle getTeamArticle() {
        return teamArticle;
    }

    public void setTeamArticle(final TeamArticle teamArticle) {
        this.teamArticle = teamArticle;
        // 무한루프에 빠지지 않도록 체크
        if (!teamArticle.getTeamArticleComments().contains(this)) {
            teamArticle.getTeamArticleComments().add(this);
        }
    }
}
