package back.springbootdeveloper.seungchan.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResDto {
    private String accessToken;

    public LoginResDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
