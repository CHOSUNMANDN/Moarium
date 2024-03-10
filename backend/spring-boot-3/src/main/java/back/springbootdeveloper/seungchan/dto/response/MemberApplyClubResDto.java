package back.springbootdeveloper.seungchan.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberApplyClubResDto {

  List<CustomInformation> customInformations;
  private String clubName;
}
