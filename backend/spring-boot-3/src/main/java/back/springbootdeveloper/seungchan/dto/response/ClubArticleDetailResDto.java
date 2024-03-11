package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubArticleDetailResDto {

  private String isClubArticleAuthor;
  private String clubArticleTitle;
  private String clubArticleContent;
  private String clubArticleLikeNumber;
  private String clubArticleCommentNumber;
  private String clubArticleDate;
  private String clubArticleAnswerSuggestion;
  private String clubArticleAnswerCheck;
  private String clubArticleClassification;
  private List<ClubArticleCommentInformation> clubArticleCommentInformations;
}
