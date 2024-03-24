package back.springbootdeveloper.seungchan.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;

public class DayUtil {

  /**
   * 현재 날짜에 해당하는 요일을 반환합니다.
   *
   * @return 현재 날짜에 해당하는 요일
   */
  public static DayOfWeek getTodayDayOfWeek() {
    // 현재 날짜를 가져옵니다.
    LocalDate today = LocalDate.now();

    // 현재 날짜에 해당하는 요일을 가져옵니다.
    return today.getDayOfWeek();
  }
}
