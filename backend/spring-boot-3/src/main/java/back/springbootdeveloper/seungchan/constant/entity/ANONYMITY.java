package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum ANONYMITY {
  ANONYMOUS("ANONYMOUS"), // 익명
  REAL_NAME("REAL_NAME"); // 실명

  private String state;

  ANONYMITY(String state) {
    this.state = state;
  }

  public boolean is(String str) {
    return this.state.equals(str);
  }
}
