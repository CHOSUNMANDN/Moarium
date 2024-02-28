package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubArticleAnswerResDto {
    private String clubArticleTitle;
    private String clubArticleContent;
    private String clubArticleLikeNumber;
    private String clubArticleCommentNumber;
    private String clubArticleDate;
}
