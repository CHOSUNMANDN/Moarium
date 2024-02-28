package back.springbootdeveloper.seungchan.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Builder
public class GoogleOAuthProfile {
    public String email;

    public GoogleOAuthProfile(String email) {
        this.email = email;
    }
}
