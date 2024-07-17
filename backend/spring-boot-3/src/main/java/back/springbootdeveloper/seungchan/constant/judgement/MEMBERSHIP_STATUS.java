package back.springbootdeveloper.seungchan.constant.judgement;

import lombok.Getter;

@Getter
public enum MEMBERSHIP_STATUS {
  MEMBER("MEMBER"),
  NON_MEMBER("NON_MEMBER");

  private String state;

  MEMBERSHIP_STATUS(final String state) {
    this.state = state;
  }
}
