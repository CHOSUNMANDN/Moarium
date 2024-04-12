package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.Member;
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
  private String name;
  private String nickName;
  private String email;
  private String major;
  private String studentId;

  public Member getMemberEntity() {
    StringBuilder sb = new StringBuilder(name.trim());

    return Member.builder()
        .firstName(String.valueOf(sb.charAt(FIRST_INDEX)))
        .lastName(sb.substring(FIRST_INDEX + 1).trim())
        .nickName(nickName.trim())
        .email(email.trim())
        .major(major.trim())
        .studentId(studentId.trim())
        .build();
  }

}
