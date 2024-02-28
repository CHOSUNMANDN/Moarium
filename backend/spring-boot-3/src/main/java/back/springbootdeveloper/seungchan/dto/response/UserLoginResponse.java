package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
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
