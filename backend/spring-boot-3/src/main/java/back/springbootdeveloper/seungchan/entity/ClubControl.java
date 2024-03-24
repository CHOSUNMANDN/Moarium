package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_control")
public class ClubControl {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_control_id")
  private Long clubControlId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "vacation_token_control_id")
  private VacationTokenControl vacationTokenControl;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "attendance_week_id")
  private AttendanceWeek attendanceWeek;

  @OneToOne(mappedBy = "clubControl")
  private Club club;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clubControl")
  private List<CustomClubApplyInformation> customClubApplyInformations = new ArrayList<>();

  /**
   * 휴가 토큰 컨트롤을 설정하는 메서드입니다. 동시에 이 휴가 토큰 컨트롤이 해당 클럽 컨트롤과 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param vacationTokenControl 설정할 휴가 토큰 컨트롤
   */
  public void setVacationTokenControl(final VacationTokenControl vacationTokenControl) {
    this.vacationTokenControl = vacationTokenControl;

    if (vacationTokenControl.getClubControl() != this) { // null 체크 추가
      vacationTokenControl.setClubControl(this);
    }
  }

  /**
   * 출석 주를 설정하는 메서드입니다. 동시에 이 출석 주가 해당 클럽 컨트롤과 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param attendanceWeek 설정할 출석 주
   */
  public void setAttendanceWeek(final AttendanceWeek attendanceWeek) {
    this.attendanceWeek = attendanceWeek;

    if (attendanceWeek.getClubControl() != this) { // null 체크 추가
      attendanceWeek.setClubControl(this);
    }
  }

  /**
   * 클럽을 설정하는 메서드입니다. 동시에 이 클럽이 해당 클럽 컨트롤과 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param club 설정할 클럽
   */
  public void setClub(final Club club) {
    this.club = club;

    if (club.getClubControl() != this) { // null 체크 추가
      club.setClubControl(this);
    }
  }

  public void addClubMemberCustomInformations(
      final CustomClubApplyInformation customClubApplyInformation) {
    this.customClubApplyInformations.add(customClubApplyInformation);

    if (customClubApplyInformation.getClubControl() != this) { // 무한루프에 빠지지 않도록 체크
      customClubApplyInformation.setClubControl(this);
    }
  }
}
