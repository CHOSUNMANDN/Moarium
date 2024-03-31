package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApplyMemberToClubReqDto {

  @NotBlank(message = "{validation.selfintroduction.notblank}")
  private String selfIntroduction;
  @Valid
  private List<CustomInformationReqForm> customInformations;
}
