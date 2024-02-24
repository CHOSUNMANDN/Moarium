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

    public void updateVacationTokenControl(Integer vacationTokenControl) {
        this.vacationTokenControl = vacationTokenControl;
    }

    public void setClubControl(final ClubControl clubControl) {
        this.clubControl = clubControl;

        if (clubControl.getVacationTokenControl() != this) { // null 체크 추가
            clubControl.setVacationTokenControl(this);
        }
    }
}
