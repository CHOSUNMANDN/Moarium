package back.springbootdeveloper.seungchan.constant.filter;

public enum CustomHttpStatus {
  WEEKEND(-480, "Weekend"),
  USER_NOT_EXIST(-481, "User Not Exist"),
  DATA_VALID(-482, "Data Valid"),
  PASSWORD_CONFIRMATION(-483, "Password Confirmation"),
  UPDATE_FAILED(-484, "Update Failed"),
  EMAIL_SAME_MATCH(-485, "Email Same Match"),
  PASSWORD_MISS_MATCHES(-486, "Password Miss Matches"),
  ENTITY_NOT_FOUND(-487, "Not Found Entity"),
  INVALID_SELECTION_CLASSIFICATION(-488, "Bad Selection Classification"),
  INVALID_NOT_LEADER_MEMBER(-489, "Bad Not Leader Of Club");
  private final int value;


  private final String reasonPhrase;

  CustomHttpStatus(int value, String reasonPhrase) {
    this.value = value;
    this.reasonPhrase = reasonPhrase;
  }

  public int value() {
    return this.value;
  }

  public String getReasonPhrase() {
    return this.reasonPhrase;
  }
}
