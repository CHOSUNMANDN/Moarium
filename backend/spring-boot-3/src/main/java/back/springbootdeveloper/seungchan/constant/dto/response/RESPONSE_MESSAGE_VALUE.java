package back.springbootdeveloper.seungchan.constant.dto.response;

public class RESPONSE_MESSAGE_VALUE {

  public static String SUCCESS_UPDATE_VACATION_TOKEN(String memberName, Integer vacationToken) {
    return memberName + "에게 휴가 " + vacationToken + "일을 부여했습니다.";
  }

  public static String FAIL_UPDATE_VACATION_TOKEN(String memberName) {
    return memberName + "에게 휴가 부여를 실패하였습니다.";
  }

  public static String SUCCESS_EXPULSION_MEMBER(String memberName) {
    return memberName + "님을 추방하였습니다.";
  }

  public static String SUCCESS_UPDATE_CLUB_GRADE(String memberName) {
    return memberName + "님을 휴면 회원으로 전환하였습니다.";
  }

  public static String FAIL_UPDATE_CLUB_GRADE(String memberName) {
    return memberName + "님을 휴면 회원으로 전환을 실패하였습니다.";
  }

  public static String ALREADY_DORMANT_MEMBER(String memberName) {
    return memberName + "님은 휴면 회원입니다.";
  }

  public static String SUCCESS_QUIT_CLUB(String clubName) {
    return clubName + "에서 탈퇴되었습니다.";
  }

  public static String SUCCESS_NOT_DUPLICATION_CLUBNAME(final String clubName) {
    return "'" + clubName + "' 사용 가능 이름 입니다.";
  }
}
