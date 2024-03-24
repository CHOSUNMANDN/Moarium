package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vacation_token")
public class VacationToken extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "vacation_token_id")
  private Long vacationTokenId;

  @Column(name = "vacation_token")
  private Integer vacationToken = 5;

  @Column(name = "vacation_token_date", length = 15, nullable = false)
  private String vacationTokenDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_state_id")
  private AttendanceState attendanceSate;

  @Builder
  public VacationToken(Integer vacationCount) {
    this.vacationToken = vacationCount;
  }

  @PrePersist
  protected void onCreate() {
    // https://www.daleseo.com/java8-zoned-date-time/
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.vacationTokenDate = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
  }

  /**
   * 휴가 횟수를 업데이트하는 메서드입니다.
   *
   * @param vacationCount 새로운 휴가 횟수
   */
  public void updateVacationCount(Integer vacationCount) {
    this.vacationToken = vacationCount;
  }

  /**
   * 휴가 횟수를 감소시키는 메서드입니다. 기본적으로 1을 감소시킵니다.
   */
  public void subtractVacationCount() {
    this.vacationToken = this.vacationToken - 1;
  }

  /**
   * 휴가 횟수를 주어진 숫자만큼 감소시키는 메서드입니다.
   *
   * @param number 감소시킬 휴가 횟수
   */
  public void subtractVacationCount(Integer number) {
    this.vacationToken = this.vacationToken - number;
  }

  /**
   * 휴가 횟수를 증가시키는 메서드입니다. 기본적으로 1을 증가시킵니다.
   */
  public void addVacationCount() {
    this.vacationToken = this.vacationToken + 1;
  }

  /**
   * 휴가 횟수를 주어진 숫자만큼 증가시키는 메서드입니다.
   *
   * @param number 증가시킬 휴가 횟수
   */
  public void addVacationCount(Integer number) {
    this.vacationToken = this.vacationToken + number;
  }

  /**
   * 출석 상태를 설정하는 메서드입니다. 동시에 이 휴가 토큰이 해당 출석 상태와 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param attendanceSate 설정할 출석 상태
   */
  public void setAttendanceSate(final AttendanceState attendanceSate) {
    this.attendanceSate = attendanceSate;

    if (!attendanceSate.getVacationTokens().contains(this)) { // null 체크 추가
      attendanceSate.addtVacationToken(this);
    }
  }
}
