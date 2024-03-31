package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomInformationReqForm {

  // club의 club Custom Apply 엔티티의 Id
  private Long customInformationId;
  @NotBlank(message = "{validation.customContent.notblank}")
  private String customContent;
}
