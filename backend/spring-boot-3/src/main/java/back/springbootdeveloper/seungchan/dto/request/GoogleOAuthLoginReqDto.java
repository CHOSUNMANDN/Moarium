package back.springbootdeveloper.seungchan.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // camel case -> snake case
public class GoogleOAuthLoginReqDto {

  private String clientId;
  private String redirectUri;
  private String clientSecret;
  private String grantType;
  private String code;
}
