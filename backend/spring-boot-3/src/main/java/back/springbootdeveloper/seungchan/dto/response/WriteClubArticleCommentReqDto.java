package back.springbootdeveloper.seungchan.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WriteClubArticleCommentReqDto {

  @NotBlank
  private String clubArticleCommentContent;
  @NotBlank
  private String anonymity;
}
