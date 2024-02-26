package back.springbootdeveloper.seungchan.constant.dto.response;

public class RESPONSE_MESSAGE_VALUE {
    public static String SUCCESS_UPDATE_VACATION_TOKEN(String memberName, Integer vacationToken) {
        return memberName + "에게 휴가 " + vacationToken + "일을 부여했습니다.";
    }

    public static String FAIL_UPDATE_VACATION_TOKEN(String memberName) {
        return memberName + "에게 휴가 부여를 실패하였습니다.";
    }
}
