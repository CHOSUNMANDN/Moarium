package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.constant.entity.CHECK;
import back.springbootdeveloper.seungchan.constant.judgement.MEMBERSHIP_STATUS;
import lombok.Builder;
import lombok.Data;

@Data
public class LoginResDto {

  private String accessToken;
  private String memberStatus;

  public LoginResDto(String accessToken) {
    this.memberStatus = MEMBERSHIP_STATUS.MEMBER.getState();
    this.accessToken = accessToken;
  }

  public LoginResDto(MEMBERSHIP_STATUS membershipStatus) {
    this.memberStatus = MEMBERSHIP_STATUS.NON_MEMBER.getState();
    this.accessToken = "";
  }
}
