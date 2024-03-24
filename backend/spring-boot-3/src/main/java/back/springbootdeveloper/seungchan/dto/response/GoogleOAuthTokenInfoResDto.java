package back.springbootdeveloper.seungchan.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleOAuthTokenInfoResDto {

  private String azp;
  private String aud;
  private String sub;
  private String exp;
  private String scope;
  private String expiresIn;
  private String email;
  private String emailVerified;
  private String accessType;

}
