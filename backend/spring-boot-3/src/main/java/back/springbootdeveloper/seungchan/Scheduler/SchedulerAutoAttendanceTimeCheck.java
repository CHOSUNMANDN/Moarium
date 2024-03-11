package back.springbootdeveloper.seungchan.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

// @Scheduled(cron = "0/1 * * * * *") // 1초마다 테스트

@Component
public class SchedulerAutoAttendanceTimeCheck {

  private void printDateAtNow(String methodName) {
    System.out.println("실행된 함수 " + methodName + " : " + new Date());
  }

  /**
   * 매일 오전 9시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 9 * * *")
  public void autoCheckAttendanceTime09() {
    // 장기휴가 처리, 출석처리
    printDateAtNow("autoCheckAttendanceTime09");
  }


  /**
   * 매일 오전 10시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 10 * * *")
  public void autoCheckAttendanceTime10() {
    printDateAtNow("autoCheckAttendanceTime10");
  }

  /**
   * 매일 오전 11시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 11 * * *")
  public void autoCheckAttendanceTime11() {
    printDateAtNow("autoCheckAttendanceTime11");
  }

  /**
   * 매일 오전 12시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 12 * * *")
  public void autoCheckAttendanceTime12() {
    printDateAtNow("autoCheckAttendanceTime12");
  }

  /**
   * 매일 오후 1시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 13 * * *")
  public void autoCheckAttendanceTime13() {
    printDateAtNow("autoCheckAttendanceTime13");
  }

  /**
   * 매일 오후 2시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 14 * * *")
  public void autoCheckAttendanceTime14() {
    printDateAtNow("autoCheckAttendanceTime14");
  }

  /**
   * 매일 오후 3시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 15 * * *")
  public void autoCheckAttendanceTime15() {
    printDateAtNow("autoCheckAttendanceTime15");
  }

  /**
   * 매일 오후 4시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 16 * * *")
  public void autoCheckAttendanceTime16() {
    printDateAtNow("autoCheckAttendanceTime16");
  }

  /**
   * 매일 오후 5시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 17 * * *")
  public void autoCheckAttendanceTime17() {
    printDateAtNow("autoCheckAttendanceTime17");
  }

  /**
   * 매일 오후 6시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 18 * * *")
  public void autoCheckAttendanceTime18() {
    printDateAtNow("autoCheckAttendanceTime18");
  }

  /**
   * 매일 오후 7시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 19 * * *")
  public void autoCheckAttendanceTime19() {
    printDateAtNow("autoCheckAttendanceTime19");
  }


  /**
   * 매일 오후 8시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 20 * * *")
  public void autoCheckAttendanceTime20() {
    printDateAtNow("autoCheckAttendanceTime20");
  }


  /**
   * 매일 오후 9시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 21 * * *")
  public void autoCheckAttendanceTime21() {
    printDateAtNow("autoCheckAttendanceTime21");
  }

  /**
   * 매일 오후 10시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 22 * * *")
  public void autoCheckAttendanceTime22() {
    printDateAtNow("autoCheckAttendanceTime22");
  }

  /**
   * 매일 오후 11시 10분 출석에 대한 여부를 확인한다.
   */
  @Scheduled(cron = "0 10 23 * * *")
  public void autoCheckAttendanceTime23() {
    printDateAtNow("autoCheckAttendanceTime23");
  }

  /**
   * 출석을 원하는 시간에 출석을 하지않으면 결석처리한다.
   *
   * @param attendanceWantTimeOfUser 출석을 원하는 시간 ( "09" )
   */
  private void autoCheckAttendanceTime(String attendanceWantTimeOfUser) {
  }

  /**
   * 예외 사항 -> 장기휴가를 신청한 인원들은 휴가처리를 하였다.
   */
  private void autoCheckExceptionAttendance() {
  }

  private boolean sameAttendanceTime(String attendanceTime, String targetAttendanceTime) {
    return attendanceTime.equals(targetAttendanceTime);
  }
}
