package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum FAVORITE_CHECK {
  CHECK("CHECK"), UNCHECK("UN_CHECK");

  private String state;

  FAVORITE_CHECK(final String state) {
    this.state = state;
  }
}
