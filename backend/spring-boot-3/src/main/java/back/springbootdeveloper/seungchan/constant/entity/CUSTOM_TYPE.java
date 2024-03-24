package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum CUSTOM_TYPE {
  TEXT("TEXT"),
  CHECK("CHECK");

  private String type;

  CUSTOM_TYPE(final String type) {
    this.type = type;
  }
}
