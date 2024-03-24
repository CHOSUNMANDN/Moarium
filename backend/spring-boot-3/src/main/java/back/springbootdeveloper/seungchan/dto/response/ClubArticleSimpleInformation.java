package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubArticleSimpleInformation {

  private String clubArticleClassification;
  private String clubArticleTitle;
  private String clubArticleAuthorName;
  private String clubArticleDate;
  private String clubArticleCommentCount;
  private String clubArticleAnswerCheck;
}
