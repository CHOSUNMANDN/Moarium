package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginReqDto{
    @NotBlank(message = "{validation.authCode.notblank}")
    private String authCode;
}
