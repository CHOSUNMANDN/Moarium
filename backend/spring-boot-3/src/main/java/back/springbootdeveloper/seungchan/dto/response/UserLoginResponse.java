package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private String name;
    private Long userId;
    private boolean isNuriKing;

    public UserLoginResponse(String accessToken, UserInfo user, UserUtill userUtill) {
        this.accessToken = accessToken;
        this.name = user.getName();
        this.userId = user.getUserInfoId();
        this.isNuriKing = userUtill.isNuriKing();
    }
}
