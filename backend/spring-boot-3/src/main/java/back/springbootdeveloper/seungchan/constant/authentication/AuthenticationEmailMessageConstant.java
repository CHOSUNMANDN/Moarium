package back.springbootdeveloper.seungchan.constant.authentication;

public class AuthenticationEmailMessageConstant {

  public static String CONTENT_OF_EMAIL(String email, String tempPassword) {
    return "안녕하세요😌\n" +
        "\n" +
        "저희 웹사이트에 가입해 주셔서 감사합니다. 아래는 회원 계정 정보입니다:\n" +
        "\n" +
        "- ✅ ID: " + email + "\n" +
        "- ✅ PW: " + tempPassword + "\n" +
        "\n" +
        "만약 본인이 해당 웹사이트에 가입하지 않았다면, 이 이메일을 무시하셔도 됩니다.\n" +
        "\n" +
        "감사합니다. 저희 서비스를 이용해 주셔서 기쁩니다!😍";
  }

  public static String SUBJECT_OF_EMAIL() {
    return "😎: 웹사이트에 가입해 주셔서 감사합니다. 회원님의 ID와 임시 비밀번호를 확인 부탁드립니다.🥰";
  }

  public static String CONTENT_OF_FIND_USER_EMAIL(String email) {
    return "안녕하세요😌\n" +
        "\n" +
        "저희 웹사이트에 가입해 주셔서 감사합니다. 아래는 회원 계정 정보입니다:\n" +
        "\n" +
        "- ✅ ID: " + email + "\n" +
        "\n" +
        "만약 본인이 해당 웹사이트에 가입하지 않았다면, 이 이메일을 무시하셔도 됩니다.\n" +
        "\n" +
        "감사합니다. 저희 서비스를 이용해 주셔서 기쁩니다!😍";
  }

  public static String SUBJECT_OF_FIND_USER_EMAIL() {
    return "😎: 웹사이트에 가입해 주셔서 감사합니다. 회원님의 email을 확인 부탁드립니다.🥰";
  }
}
