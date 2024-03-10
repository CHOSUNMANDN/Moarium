package back.springbootdeveloper.seungchan.util;

import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BaseResponseBodyUtiil {

  /**
   * 성공적인 기본 응답 바디를 반환합니다.
   *
   * @return 성공적인 기본 응답 바디를 담은 ResponseEntity입니다.
   */
  public static ResponseEntity<BaseResponseBody> BaseResponseBodySuccess() {
    return new ResponseEntity<>(
        BaseResponseBody.builder()
            .message("SUCCESS")
            .httpStatus(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
            .build(),
        HttpStatus.OK
    );
  }

  /**
   * 성공적인 기본 응답 바디를 포함한 ResponseEntity를 반환합니다.
   *
   * @param message 응답 메시지로 설정할 문자열입니다.
   * @return 성공적인 기본 응답 바디를 담은 ResponseEntity입니다.
   */
  public static ResponseEntity<BaseResponseBody> BaseResponseBodySuccess(String message) {
    return new ResponseEntity<>(
        BaseResponseBody.builder()
            .message(message)
            .httpStatus(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
            .build(),
        HttpStatus.OK
    );
  }

  /**
   * 실패한 기본 응답 바디를 포함한 ResponseEntity를 반환합니다.
   *
   * @param message 응답 메시지로 설정할 문자열입니다.
   * @return 실패한 기본 응답 바디를 담은 ResponseEntity입니다.
   */
  public static ResponseEntity<BaseResponseBody> BaseResponseBodyFailure(String message) {
    return new ResponseEntity<>(
        BaseResponseBody.builder()
            .message(message)
            .httpStatus(HttpStatus.BAD_REQUEST)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
            .build(),
        HttpStatus.OK
    );
  }

  /**
   * 실패한 기본 응답 바디를 포함한 ResponseEntity를 반환합니다.
   *
   * @return 실패한 기본 응답 바디를 담은 ResponseEntity입니다.
   */
  public static ResponseEntity<BaseResponseBody> BaseResponseBodyFailure() {
    return new ResponseEntity<>(
        BaseResponseBody.builder()
            .message("FAIL")
            .httpStatus(HttpStatus.BAD_REQUEST)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
            .build(),
        HttpStatus.OK
    );
  }
}
