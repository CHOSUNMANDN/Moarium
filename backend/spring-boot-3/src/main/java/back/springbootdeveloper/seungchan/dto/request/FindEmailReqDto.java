package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindEmailReqDto {

  @NotBlank(message = "{validation.name.notblank}")
  private String name;

  @Email(message = "{validation.email.invalid}")
  @NotBlank(message = "{validation.email.notblank}")
  private String authenticationEmail;

  @NotBlank(message = "{validation.phonenum.notblank}")
  private String phoneNum;
}
