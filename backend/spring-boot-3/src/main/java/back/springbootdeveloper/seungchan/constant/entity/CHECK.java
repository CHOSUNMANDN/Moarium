package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum CHECK {
  CHECK("CHECK"), UNCHECK("UN_CHECK");

  private String state;

  CHECK(final String state) {
    this.state = state;
  }
}
