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

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateAnonymity(ANONYMITY anonymity) {
        this.anonymity = anonymity;
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


    public ClubArticle getClubArticle() {
        return clubArticle;
    }

    public void setClubArticle(final ClubArticle clubArticle) {
        this.clubArticle = clubArticle;
        // 무한루프에 빠지지 않도록 체크
        if (!clubArticle.getClubArticleComments().contains(this)) {
            clubArticle.getClubArticleComments().add(this);
        }
    }
}
