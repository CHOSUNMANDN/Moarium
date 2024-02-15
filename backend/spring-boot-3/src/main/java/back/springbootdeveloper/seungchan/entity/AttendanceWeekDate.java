package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_week_date")
public class AttendanceWeekDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_week_date_id")
    private Long attendanceWeekDateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "monday", length = 15, nullable = false)
    private ATTENDANCE_STATE monday;

    @Enumerated(EnumType.STRING)
    @Column(name = "tuesday", length = 15, nullable = false)
    private ATTENDANCE_STATE tuesday;

    @Enumerated(EnumType.STRING)
    @Column(name = "wednesday", length = 15, nullable = false)
    private ATTENDANCE_STATE wednesday;

    @Enumerated(EnumType.STRING)
    @Column(name = "thursday", length = 15, nullable = false)
    private ATTENDANCE_STATE thursday;

    @Enumerated(EnumType.STRING)
    @Column(name = "friday", length = 15, nullable = false)
    private ATTENDANCE_STATE friday;

    @Enumerated(EnumType.STRING)
    @Column(name = "saturday", length = 15, nullable = false)
    private ATTENDANCE_STATE saturday;

    @Enumerated(EnumType.STRING)
    @Column(name = "sunday", length = 15, nullable = false)
    private ATTENDANCE_STATE sunday;

    @Temporal(TemporalType.DATE)
    @Column(name = "monday_date", nullable = false)
    private LocalDate mondayDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "sunday_date", nullable = false)
    private LocalDate sundayDate;

    @Builder
    public AttendanceWeekDate(ATTENDANCE_STATE monday, ATTENDANCE_STATE tuesday, ATTENDANCE_STATE wednesday, ATTENDANCE_STATE thursday, ATTENDANCE_STATE friday, ATTENDANCE_STATE saturday, ATTENDANCE_STATE sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    @PrePersist
    protected void onCreate() {
        ZonedDateTime now = ZonedDateTime.now(); // 현재 날짜와 시간을 가져옵니다.
        ZonedDateTime monday = now.with(DayOfWeek.MONDAY); // 현재 주의 월요일을 가져옵니다.
        ZonedDateTime sunday = now.with(DayOfWeek.SUNDAY); // 현재 주의 일요일을 가져옵니다.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.mondayDate = LocalDate.parse(monday.format(formatter)); // "yyyy-MM-dd" 형식으로 포맷팅
        this.sundayDate = LocalDate.parse(sunday.format(formatter)); // "yyyy-MM-dd" 형식으로 포맷팅

        this.monday = ATTENDANCE_STATE.UNDECIDED;
        this.tuesday = ATTENDANCE_STATE.UNDECIDED;
        this.wednesday = ATTENDANCE_STATE.UNDECIDED;
        this.thursday = ATTENDANCE_STATE.UNDECIDED;
        this.friday = ATTENDANCE_STATE.UNDECIDED;
        this.saturday = ATTENDANCE_STATE.UNDECIDED;
        this.sunday = ATTENDANCE_STATE.UNDECIDED;
    }

    public void updateAttendanceAtToday() {
        ZonedDateTime now = ZonedDateTime.now();
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();

        switch (currentDayOfWeek) {
            case MONDAY:
                this.monday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            case TUESDAY:
                this.tuesday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            case WEDNESDAY:
                this.wednesday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            case THURSDAY:
                this.thursday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            case FRIDAY:
                this.friday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            case SATURDAY:
                this.saturday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            case SUNDAY:
                this.sunday = ATTENDANCE_STATE.ATTENDANCE;
                break;
            default:
                break;
        }
    }

    public void updateAbsenceAtToday() {
        ZonedDateTime now = ZonedDateTime.now();
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();

        switch (currentDayOfWeek) {
            case MONDAY:
                this.monday = ATTENDANCE_STATE.ABSENCE;
                break;
            case TUESDAY:
                this.tuesday = ATTENDANCE_STATE.ABSENCE;
                break;
            case WEDNESDAY:
                this.wednesday = ATTENDANCE_STATE.ABSENCE;
                break;
            case THURSDAY:
                this.thursday = ATTENDANCE_STATE.ABSENCE;
                break;
            case FRIDAY:
                this.friday = ATTENDANCE_STATE.ABSENCE;
                break;
            case SATURDAY:
                this.saturday = ATTENDANCE_STATE.ABSENCE;
                break;
            case SUNDAY:
                this.sunday = ATTENDANCE_STATE.ABSENCE;
                break;
            default:
                break;
        }
    }

    public void updateVacationAtToday() {
        ZonedDateTime now = ZonedDateTime.now();
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();

        switch (currentDayOfWeek) {
            case MONDAY:
                this.monday = ATTENDANCE_STATE.VACATION;
                break;
            case TUESDAY:
                this.tuesday = ATTENDANCE_STATE.VACATION;
                break;
            case WEDNESDAY:
                this.wednesday = ATTENDANCE_STATE.VACATION;
                break;
            case THURSDAY:
                this.thursday = ATTENDANCE_STATE.VACATION;
                break;
            case FRIDAY:
                this.friday = ATTENDANCE_STATE.VACATION;
                break;
            case SATURDAY:
                this.saturday = ATTENDANCE_STATE.VACATION;
                break;
            case SUNDAY:
                this.sunday = ATTENDANCE_STATE.VACATION;
                break;
            default:
                break;
        }
    }
}
