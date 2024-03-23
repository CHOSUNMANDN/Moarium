package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubArticleCommentInformation {

  private Long clubArticleCommentId;
  private String isClubArticleCommentAuthor;
  private String clubArticleCommentContent;
  private String clubArticleCommentAuthorName;
  private String clubArticleCommentDate;
  private String clubArticleCommentLike;
}
