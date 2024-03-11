package back.springbootdeveloper.seungchan.constant.dto.response;

public enum ResponseMessage {
  TEMP_PASSWORD_MESSAGE("임시 비밀번호가 성공적으로 발급되었습니다. 이메일을 확인해주세요."),
  UPDATE_PASSWORD_MESSAGE("비밀번호가 성공적으로 업데이트되었습니다."),
  UPDATE_EMAIL_MESSAGE("이메일이 성공적으로 업데이트 되었습니다."),
  FIND_EMAIL_OK("이메일 주소 찾기가 완료되었습니다. 이메일을 확인해주세요."),
  REGISTRATION_TIMETABLE_COMPLETE("시간표 등록이 완료 되었습니다."),
  OAUTH_LOGIN_SUCCESS("3rd 파티 로그인을 성공했습니다."),
  PASS_TODAY_ATTENDANCE("출석이 성공적으로 완료되었습니다."),
  BAD_TODAY_ATTENDANCE("출석에 실패했습니다."),
  SUCCESS_TOGLE_MEMBER_GRADE("활동회원으로 전환되었습니다."),
  SUCCESS_TOGLE_DORMANT_GRADE("휴면회원으로 전환되었습니다."),
  BAD_TOGLE_GRADE("등급 전환에 실패했습니다."),
  UPDATE_CLUB_ARTICLE("게시글 수정 되었습니다."),
  BAD_UPDATE_CLUB_ARTICLE("게시글 수정에 실패 하였습니다."),
  SUCCESS_DELETE_CLUB_ARTICLE("글이 삭제되었습니다."),
  BAD_DELETE_CLUB_ARTICLE("삭제에 실패하였습니다."),
  BAD_REPORT_CLUB_ARTICLE("신고에 실패하였습니다."),
  BAD_ALREADY_REPORT_CLUB_ARTICLE("신고에 실패하였습니다."),
  SUCCESS_REPORT_CLUB_ARTICLE("신고 접수 되었습니다.."),
  SUCCESS_SUGGESTION_ANSWER("답변이 완료 되었습니다."),
  BAD_SUGGESTION_ANSWER("답변이 실패 되었습니다. 대표 부대표만 가능합니다."),
  BAD_ALREADY_TODAY_UPDATE_ATTENDANCE_STATE("이미 결석, 휴가를 하였 습니다."),
  BAD_DORMANT_TODAY_ATTENDANCE_STATE("휴면 회원은 출석을 할 수 없습니다."),
  BAD_NOT_LEADER_CLUB("클럽의 대표가 아닙니다."),
  BAD_TARGET_LEADER_MEMBER("대표을 대상으로 할수 없는 기능입니다."),
  BAD_REQUEST_NOT_CLUB_CHECK_STATE("클럽 출석 체크 지정일이 아닙니다."),
  BAD_NOT_SAME_LOGIN_TARGET_MEMBER("타인의 계정에 시도 할 수 없는 기능 입니다."),
  SUCCESS_UPDATE_SUGGESTION_ANSWER("답변 수정이 완료 되었습니다."),
  BAD_UPDATE_SUGGESTION_ANSWER("답변 수정이 실패 하였습니다. 대표 부대표만 가능합니다."),
  SUCCESS_REGISTRATION_CLUB("팀 등록이 완료 되었습니다."),
  BAD_REGISTRATION_CLUB("팀 등록을 실패 하였습니다."),
  BAD_DUPLICATION_CLUBNAME("같은 이름의 팀 이름이 있습니다."),
  BAD_NOT_BLANK_CLUB_NAME("클럽 이름을 입력해주세요."),
  BAD_NOT_BLANK_CLUB_INTRODUCTION("클럽 자기소개를 입력해주세요"),
  SUCCESS_APPLY_CLUB("지원서 신청이 완료 되었습니다."),
  BAD_APPLY_CLUB("지원서 신청이 실패 하였습니다."),
  BAD_ALREADY_APPLY_CLUB("이미 지원서 신청을 하였습니다."),
  BAD_ALREADY_REGISTRATION_CLUB("이미 가입된 회원입니다."),
  SUCCESS_ACCEPT_TEMP_MEMBER("임시 회원을 정회원으로 전환 하였습니다."),
  BAD_ACCEPT_TEMP_MEMBER("임시 회원을 정회원으로 전환 실패 했습니다."),
  SUCCESS_REFUSE_TEMP_MEMBER("임시 회원 신청을 거부했습니다."),
  BAD_REFUSE_TEMP_MEMBER("임시 회원 신청 거부를 처리하는 동안 문제가 발생했습니다.");

  private final String message;

  ResponseMessage(String message) {
    this.message = message;
  }


  public String get() {
    return message;
  }
}
