package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateClubArticlePutDto {

  @NotBlank
  private String clubArticleUpdateTitle;
  @NotBlank
  private String clubArticleUpdateContent;
}
