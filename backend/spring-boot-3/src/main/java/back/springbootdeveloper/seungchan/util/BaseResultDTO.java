package back.springbootdeveloper.seungchan.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class BaseResultDTO<D> {

  private final HttpStatus httpStatus;
  private final Integer stateCode;
  private final String timestamp;
  private final String message;
  private final D result;

  /**
   * 메시지와 데이터를 포함하는 성공 응답
   *
   * @param message 응답에 포함될 메시지
   * @param data    응답에 포함될 데이터
   * @param <D>     데이터의 유형
   * @return 성공 응답을 나타내는 ResultDTO 인스턴스
   */
  public static <D> BaseResultDTO<D> ofSuccessWithMessage(String message, D data) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedTimestamp = dateFormat.format(new Date());

    return new BaseResultDTO<>(
        HttpStatus.OK,
        HttpStatus.OK.value(),
        formattedTimestamp,
        message,
        data
    );
  }

  /**
   * 메시지와 데이터를 포함하는 성공 응답
   *
   * @param data 응답에 포함될 데이터
   * @param <D>  데이터의 유형
   * @return 성공 응답을 나타내는 ResultDTO 인스턴스
   */
  public static <D> BaseResultDTO<D> ofSuccess(D data) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedTimestamp = dateFormat.format(new Date());

    return new BaseResultDTO<>(
        HttpStatus.OK,
        HttpStatus.OK.value(),
        formattedTimestamp,
        "SUCCESS",
        data
    );
  }
}
