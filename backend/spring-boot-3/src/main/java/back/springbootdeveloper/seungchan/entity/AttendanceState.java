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

    @OneToMany(mappedBy = "attendanceState", cascade = CascadeType.ALL)
    private List<AttendanceWeekDate> attendanceWeekDates = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vacation_token_id")
    private VacationToken vacationToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendance_check_time_id")
    private AttendanceCheckTime attendanceCheckTime;

    public void addAttendanceWeekDates(final AttendanceWeekDate attendanceWeekDate) {
        this.attendanceWeekDates.add(attendanceWeekDate);

        if (attendanceWeekDate.getAttendanceState() != this) { // null 체크 추가
            attendanceWeekDate.setAttendanceState(this);
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
