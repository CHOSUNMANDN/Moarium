package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.ClubArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyClubArticle {
    private Long clubArticleId;
    private String clubArticleTitle;
    private String clubArticleDate;
    private String clubArticleClassification;

    @Builder
    public MyClubArticle(ClubArticle clubArticle) {
        this.clubArticleId = clubArticle.getClubArticleId();
        this.clubArticleTitle = clubArticle.getTitle();
        this.clubArticleDate = clubArticle.getClubArticleDate();
        this.clubArticleClassification = clubArticle.getClassification().getSort();
    }
}
