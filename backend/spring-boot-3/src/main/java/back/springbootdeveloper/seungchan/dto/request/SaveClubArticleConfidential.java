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
public class SaveClubArticleConfidential {

  @NotBlank
  private String clubArticleTitle;
  @NotBlank
  private String clubArticleContent;
  @NotBlank
  private String anonymity;
}
