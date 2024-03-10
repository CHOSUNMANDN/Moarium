package back.springbootdeveloper.seungchan.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponse {

  private String accessToken;

  public UserLoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
