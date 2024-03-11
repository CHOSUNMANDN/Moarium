package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME;
import back.springbootdeveloper.seungchan.constant.entity.LONG_VACATION;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원들의 출석시간을 월, 화, 수 목, 금, 토, 일요일에 지정할 수 있다.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_check_time")
public class AttendanceCheckTime extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_check_time_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "monday", length = 10, nullable = false)
  private ATTENDANCE_TIME monday;

  @Enumerated(EnumType.STRING)
  @Column(name = "tuesday", length = 10, nullable = false)
  private ATTENDANCE_TIME tuesday;

  @Enumerated(EnumType.STRING)
  @Column(name = "wednesday", length = 10, nullable = false)
  private ATTENDANCE_TIME wednesday;

  @Enumerated(EnumType.STRING)
  @Column(name = "thursday", length = 10, nullable = false)
  private ATTENDANCE_TIME thursday;

  @Enumerated(EnumType.STRING)
  @Column(name = "friday", length = 10, nullable = false)
  private ATTENDANCE_TIME friday;

  @Enumerated(EnumType.STRING)
  @Column(name = "saturday", length = 10, nullable = false)
  private ATTENDANCE_TIME saturday;

  @Enumerated(EnumType.STRING)
  @Column(name = "sunday", length = 10, nullable = false)
  private ATTENDANCE_TIME sunday;

  @Enumerated(EnumType.STRING)
  @Column(name = "long_vacation", length = 15, nullable = false)
  private LONG_VACATION longVacation;

  @OneToOne(mappedBy = "attendanceCheckTime")
  private AttendanceState attendanceSate;

  @Builder
  public AttendanceCheckTime(ATTENDANCE_TIME monday, ATTENDANCE_TIME tuesday,
      ATTENDANCE_TIME wednesday, ATTENDANCE_TIME thursday, ATTENDANCE_TIME friday,
      ATTENDANCE_TIME saturday, ATTENDANCE_TIME sunday, LONG_VACATION longVacation) {
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
    this.saturday = saturday;
    this.sunday = sunday;
    this.longVacation = longVacation;
  }

  @PrePersist
  protected void onCreate() {
    this.monday = ATTENDANCE_TIME.TEN;
    this.tuesday = ATTENDANCE_TIME.TEN;
    this.wednesday = ATTENDANCE_TIME.TEN;
    this.thursday = ATTENDANCE_TIME.TEN;
    this.friday = ATTENDANCE_TIME.TEN;
    this.saturday = ATTENDANCE_TIME.TEN;
    this.sunday = ATTENDANCE_TIME.TEN;
    this.longVacation = LONG_VACATION.NOT_APPLIED;
  }

  /**
   * 월요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param monday 월요일 출석 시간
   */
  public void updateMonday(ATTENDANCE_TIME monday) {
    this.monday = monday;
  }

  /**
   * 화요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param tuesday 화요일 출석 시간
   */
  public void updateTuesday(ATTENDANCE_TIME tuesday) {
    this.tuesday = tuesday;
  }

  /**
   * 수요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param wednesday 수요일 출석 시간
   */
  public void updateWednesday(ATTENDANCE_TIME wednesday) {
    this.wednesday = wednesday;
  }

  /**
   * 목요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param thursday 목요일 출석 시간
   */
  public void updateThursday(ATTENDANCE_TIME thursday) {
    this.thursday = thursday;
  }

  /**
   * 금요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param friday 금요일 출석 시간
   */
  public void updateFriday(ATTENDANCE_TIME friday) {
    this.friday = friday;
  }

  /**
   * 토요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param saturday 토요일 출석 시간
   */
  public void updateSaturday(ATTENDANCE_TIME saturday) {
    this.saturday = saturday;
  }

  /**
   * 일요일 출석 시간을 업데이트하는 메서드입니다.
   *
   * @param sunday 일요일 출석 시간
   */
  public void updateSunday(ATTENDANCE_TIME sunday) {
    this.sunday = sunday;
  }

  /**
   * 장기 휴가를 업데이트하는 메서드입니다.
   *
   * @param longVacation 장기 휴가
   */
  public void updateLongVacation(LONG_VACATION longVacation) {
    this.longVacation = longVacation;
  }

  /**
   * 출석 상태를 설정하는 메서드입니다. 동시에 이 출석 시간이 해당 출석 상태의 검사 시간이 아닌 경우에만 설정합니다.
   *
   * @param attendanceState 출석 상태
   */
  public void setAttendanceSate(final AttendanceState attendanceState) {
    this.attendanceSate = attendanceState;

    if (attendanceState.getAttendanceCheckTime() != this) { // null 체크 추가
      attendanceState.setAttendanceCheckTime(this);
    }
  }

}
