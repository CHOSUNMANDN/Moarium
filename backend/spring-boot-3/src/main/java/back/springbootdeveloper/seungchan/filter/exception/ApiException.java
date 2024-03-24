package back.springbootdeveloper.seungchan.filter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ApiException {

  private final String message;
  private final HttpStatus httpStatus;
  private final Integer stateCode;
  private final ZonedDateTime timestamp;
}