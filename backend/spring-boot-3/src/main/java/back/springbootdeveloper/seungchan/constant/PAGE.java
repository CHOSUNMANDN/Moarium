package back.springbootdeveloper.seungchan.constant;

import lombok.Getter;

@Getter
public enum PAGE {
  BASE_PAGE_INDEX(0), PAGE_SIZE(6);

  private Integer value;

  PAGE(final Integer vlue) {
    this.value = vlue;
  }
}
