package back.springbootdeveloper.seungchan.constant.filter.exception;

public enum ExceptionMessage {
  USER_NOT_EXIST_MESSAGE("해당 사용자가 존재하지 않습니다."),
  NEW_USER_REGISTRATION_MESSAGE("신입 유저 가입에 실패하였습니다. 다시 시도해주세요."),
  WEEKEND_MESSAGE("주말은 출석 할 수 없습니다."),
  PASSWORD_CONFIRMATION("비밀번호와 비밀번호 확인란의 내용이 일치해야 합니다."),
  UPDATE_FAILED("업데이트를 완료할 수 없습니다. 다시 시도해 주세요."),
  EMAIL_SAME_MATCH("입력하신 이메일 주소가 서로 일치합니다. 다시 확인해 주세요."),
  PASSWORD_MISS_MATCH("입력하신 비밀번호와 기존 비밀번호가 일치하지 않습니다."),
  ENTITY_NOT_FOUND("데이터를 찾지 못했습니다."),
  INVALID_SELECTION_CLASSIFICATION("적절한 분류를 선택해주세요"),

  EXTERNAL_SERVICE_BAD_REQUEST("외부 API 요청에 문제가 생겼습니다."),
  EXTERNAL_SERVICE_UNAVAILABLE("외부 API server에 문제가 생겼습니다.");

  private final String message;

  ExceptionMessage(String message) {
    this.message = message;
  }

  public String get() {
    return message;
  }
}
