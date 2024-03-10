package back.springbootdeveloper.seungchan.constant;

import java.util.List;

public enum SuggestionConstantGroup {
  classification(
      List.of(SuggestionConstant.SUGGESTION, SuggestionConstant.SECRET, SuggestionConstant.FREEDOM,
          SuggestionConstant.VACATION));

  private List<SuggestionConstant> classificationTypes;

  SuggestionConstantGroup(List<SuggestionConstant> classificationTypes) {
    this.classificationTypes = classificationTypes;
  }

  public Boolean contain(String target) {
    return classificationTypes.stream().anyMatch(classification -> classification.is(target));
  }
}
