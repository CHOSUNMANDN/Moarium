package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club")
public class Club extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_id")
  private Long clubId;

  @Column(name = "club_name", length = 30, nullable = false, unique = true)
  private String clubName;

  @Column(name = "club_introduce", length = 1000, nullable = false)
  private String clubIntroduce;

  @Column(name = "club_profile_image", length = 200, nullable = false)
  private String clubProfileImage;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "club_id")
  private List<ClubIntroduceImage> clubIntroduceImages = new ArrayList<>();

  @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
  private List<AttendanceNumber> attendanceNumbers = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "club_control_id")
  private ClubControl clubControl;

  @Builder
  public Club(String clubName, String clubIntroduce, String clubProfileImage) {
    this.clubName = clubName.trim();
    this.clubIntroduce = clubIntroduce;
    this.clubProfileImage = clubProfileImage;
  }

  /**
   * 클럽 이름을 업데이트하는 메서드입니다.
   *
   * @param group 새로운 클럽 이름
   */
  public void updateClubName(String group) {
    this.clubName = group;
  }

  /**
   * 클럽 소개를 업데이트하는 메서드입니다.
   *
   * @param clubIntroduce 새로운 클럽 소개
   */
  public void updateClubIntroduce(String clubIntroduce) {
    this.clubIntroduce = clubIntroduce;
  }

  /**
   * 클럽 프로필 이미지를 업데이트하는 메서드입니다.
   *
   * @param clubProfileImage 새로운 클럽 프로필 이미지
   */
  public void updateClubProfileImage(String clubProfileImage) {
    this.clubProfileImage = clubProfileImage;
  }

  /**
   * 클럽 소개 이미지를 추가하는 메서드입니다.
   *
   * @param clubIntroduceImage 추가할 클럽 소개 이미지
   */
  public void addClubIntroduceImage(final ClubIntroduceImage clubIntroduceImage) {
    this.clubIntroduceImages.add(clubIntroduceImage);
  }

  /**
   * 출석 번호를 추가하는 메서드입니다. 동시에 이 출석 번호가 해당 클럽과 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param attendanceNumber 추가할 출석 번호
   */
  public void addAttendanceNumber(final AttendanceNumber attendanceNumber) {
    this.attendanceNumbers.add(attendanceNumber);

    if (attendanceNumber.getClub() != this) { // null 체크 추가
      attendanceNumber.setClub(this);
    }
  }

  /**
   * 클럽 컨트롤을 설정하는 메서드입니다. 동시에 이 클럽 컨트롤이 해당 클럽과 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param clubControl 설정할 클럽 컨트롤
   */
  public void setClubControl(final ClubControl clubControl) {
    this.clubControl = clubControl;

    if (clubControl.getClub() != this) { // null 체크 추가
      clubControl.setClub(this);
    }
  }

  /**
   * 현재 클럽 이름과 주어진 클럽 이름이 동일한지 여부를 확인합니다.
   *
   * @param targetClubName 비교 대상이 되는 클럽 이름
   * @return 클럽 이름이 동일하면 true, 그렇지 않으면 false
   */
  public boolean isSameName(final String targetClubName) {
    return this.clubName.equals(targetClubName);
  }
}
