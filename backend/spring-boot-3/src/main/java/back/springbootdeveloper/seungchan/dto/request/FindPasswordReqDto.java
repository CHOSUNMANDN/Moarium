package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindPasswordReqDto {

  @NotBlank(message = "{validation.name.notblank}")
  private String name;

  @NotBlank(message = "{validation.email.notblank}")
  @Email(message = "{validation.email.invalid}")
  private String email;

  @NotBlank(message = "{validation.authentication.email.notblank}")
  @Email(message = "{validation.authentication.email.invalid}")
  private String authenticationEmail;
}
