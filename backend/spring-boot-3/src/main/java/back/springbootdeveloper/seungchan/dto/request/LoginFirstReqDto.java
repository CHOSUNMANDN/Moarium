package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.Member;
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
public class LoginFirstReqDto {

  private final Integer FIRST_INDEX = 0;
  @NotBlank(message = "{validation.name.notblank}")
  private String name;
  @NotBlank(message = "{validation.nickName.notblank}")
  private String nickName;
  @NotBlank(message = "{validation.email.notblank}")
  @Email(message = "{validation.email.invalid}")
  private String email;
  @NotBlank(message = "{validation.major.notblank}")
  private String major;
  @NotBlank(message = "{validation.studentId.notblank}")
  private String studentId;
  private String authenticationEmail;
  private String authenticationNickName;
}
