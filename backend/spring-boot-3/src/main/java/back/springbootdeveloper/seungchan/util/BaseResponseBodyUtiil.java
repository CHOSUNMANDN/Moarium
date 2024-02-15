package back.springbootdeveloper.seungchan.util;

import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BaseResponseBodyUtiil {

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
}
