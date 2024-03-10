package back.springbootdeveloper.seungchan.dto.request;

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
  private String customContent;
}
