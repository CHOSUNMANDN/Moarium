package back.springbootdeveloper.seungchan.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vacation_token_control")
public class VacationTokenControl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "vacation_token_control_id")
  private Long vacationTokenControlId;

  @Column(name = "vacation_token_control")
  private Integer vacationTokenControl = 5;

  @OneToOne(mappedBy = "vacationTokenControl")
  private ClubControl clubControl;

  /**
   * 휴가 토큰 컨트롤을 업데이트하는 메서드입니다.
   *
   * @param vacationTokenControl 새로운 휴가 토큰 컨트롤
   */
  public void updateVacationTokenControl(Integer vacationTokenControl) {
    this.vacationTokenControl = vacationTokenControl;
  }

  /**
   * 클럽 컨트롤을 설정하는 메서드입니다. 동시에 이 클럽 컨트롤이 해당 휴가 토큰 컨트롤과 연결되어 있지 않은 경우에만 연결합니다.
   *
   * @param clubControl 설정할 클럽 컨트롤
   */
  public void setClubControl(final ClubControl clubControl) {
    this.clubControl = clubControl;

    if (clubControl.getVacationTokenControl() != this) { // null 체크 추가
      clubControl.setVacationTokenControl(this);
    }
  }

}
