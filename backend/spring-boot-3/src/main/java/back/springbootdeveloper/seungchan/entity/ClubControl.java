package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
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

    public void setVacationTokenControl(final VacationTokenControl vacationTokenControl) {
        this.vacationTokenControl = vacationTokenControl;

        if (vacationTokenControl.getClubControl() != this) { // null 체크 추가
            vacationTokenControl.setClubControl(this);
        }
    }

    public void setAttendanceWeek(final AttendanceWeek attendanceWeek) {
        this.attendanceWeek = attendanceWeek;

        if (attendanceWeek.getClubControl() != this) { // null 체크 추가
            attendanceWeek.setClubControl(this);
        }
    }

    public void setClub(final Club club) {
        this.club = club;

        if (club.getClubControl() != this) { // null 체크 추가
            club.setClubControl(this);
        }
    }
}
