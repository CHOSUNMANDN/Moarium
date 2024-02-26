package back.springbootdeveloper.seungchan.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_state")
public class AttendanceState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_state_id")
    private Long attendanceStateId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendance_week_date_id")
    private AttendanceWeekDate attendanceWeekDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vacation_token_id")
    private VacationToken vacationToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendance_check_time_id")
    private AttendanceCheckTime attendanceCheckTime;

    public void setAttendanceWeekDate(final AttendanceWeekDate attendanceWeekDate) {
        this.attendanceWeekDate = attendanceWeekDate;

        if (attendanceWeekDate.getAttendanceSate() != this) { // null 체크 추가
            attendanceWeekDate.setAttendanceSate(this);
        }
    }

    public void setVacationToken(final VacationToken vacationToken) {
        this.vacationToken = vacationToken;

        if (vacationToken.getAttendanceSate() != this) { // null 체크 추가
            vacationToken.setAttendanceSate(this);
        }
    }

    public void setAttendanceCheckTime(final AttendanceCheckTime attendanceCheckTime) {
        this.attendanceCheckTime = attendanceCheckTime;

        if (attendanceCheckTime.getAttendanceSate() != this) { // null 체크 추가
            attendanceCheckTime.setAttendanceSate(this);
        }
    }
}
