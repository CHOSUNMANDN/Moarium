package back.springbootdeveloper.seungchan.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BaseResponseBody {

  private final String message;
  private final HttpStatus httpStatus;
  private final Integer statusCode;
  private final ZonedDateTime timestamp;
}