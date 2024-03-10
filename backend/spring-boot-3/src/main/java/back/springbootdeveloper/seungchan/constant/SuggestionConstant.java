package back.springbootdeveloper.seungchan.constant;

import lombok.Getter;

@Getter
public enum SuggestionConstant {
  SUGGESTION("건의"),
  SECRET("비밀"),
  FREEDOM("자유"),
  VACATION("휴가");

  private String classification;

  SuggestionConstant(String classification) {
    this.classification = classification;
  }

  public Boolean is(String target) {
    return classification.equals(target);
  }
}