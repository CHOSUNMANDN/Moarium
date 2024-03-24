package back.springbootdeveloper.seungchan.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_state")
public class AttendanceState {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_state_id")
  private Long attendanceStateId;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "attendanceState", cascade = CascadeType.ALL)
  private List<AttendanceWeekDate> attendanceWeekDates = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "attendanceSate", cascade = CascadeType.ALL)
  private List<VacationToken> vacationTokens = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "attendance_check_time_id")
  private AttendanceCheckTime attendanceCheckTime;

  /**
   * 출석 주차 날짜를 추가하는 메서드입니다. 동시에 이 출석 주차 날짜가 해당 출석 상태와 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param attendanceWeekDate 추가할 출석 주차 날짜
   */
  public void addAttendanceWeekDates(final AttendanceWeekDate attendanceWeekDate) {
    this.attendanceWeekDates.add(attendanceWeekDate);

    if (attendanceWeekDate.getAttendanceState() != this) { // null 체크 추가
      attendanceWeekDate.setAttendanceState(this);
    }
  }

  /**
   * 휴가 토큰을 추가하는 메서드입니다. 동시에 이 휴가 토큰이 해당 출석 상태와 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param vacationToken 추가할 휴가 토큰
   */
  public void addtVacationToken(final VacationToken vacationToken) {
    this.vacationTokens.add(vacationToken);

    if (vacationToken.getAttendanceSate() != this) { // null 체크 추가
      vacationToken.setAttendanceSate(this);
    }
  }

  /**
   * 출석 시간을 설정하는 메서드입니다. 동시에 이 출석 시간이 해당 출석 상태와 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param attendanceCheckTime 설정할 출석 시간
   */
  public void setAttendanceCheckTime(final AttendanceCheckTime attendanceCheckTime) {
    this.attendanceCheckTime = attendanceCheckTime;

    if (attendanceCheckTime.getAttendanceSate() != this) { // null 체크 추가
      attendanceCheckTime.setAttendanceSate(this);
    }
  }

}
