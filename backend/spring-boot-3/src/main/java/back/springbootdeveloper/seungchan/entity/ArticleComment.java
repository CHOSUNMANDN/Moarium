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
@Table(name = "article_comment")
@DynamicInsert // @ColumnDefault 사용 필수insert할시 Null 배제
@DynamicUpdate // update할시 Null 배재
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_comment_id")
    private Long articleCommentId;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(name = "comment_date", nullable = false)
    private LocalDate commentDate;

    @ColumnDefault(value = "0")
    @Column(name = "declaration_count")
    private Integer declarationCount;

    @ColumnDefault(value = "0")
    @Column(name = "like_count")
    private Integer likeCount;

    @Builder
    public ArticleComment(String content) {
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

    public void subtractDeclaration() {
        if (this.declarationCount > 0) {
            this.declarationCount = this.declarationCount - 1;
        }
    }

    public void addDeclaration() {
        this.declarationCount = this.declarationCount + 1;
    }

    public void subtractLike() {
        if (this.likeCount > 0) {
            this.likeCount = this.likeCount - 1;
        }
    }

    public void addLike() {
        this.likeCount = this.likeCount + 1;
    }

    public String getCommentDate() {
        return this.commentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
