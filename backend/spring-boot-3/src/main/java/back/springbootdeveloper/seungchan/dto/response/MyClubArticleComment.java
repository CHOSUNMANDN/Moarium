package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyClubArticleComment {

  private Long clubArticleId;
  private String clubArticleTitle;
  private String articleComment;
  private String articleCommentDate;

  @Builder
  public MyClubArticleComment(ClubArticleComment clubArticleComment) {
    this.clubArticleId = clubArticleComment.getClubArticle().getClubArticleId();
    this.clubArticleTitle = clubArticleComment.getClubArticle().getTitle();
    this.articleComment = clubArticleComment.getContent();
    this.articleCommentDate = clubArticleComment.getCommentDate();
  }
}
