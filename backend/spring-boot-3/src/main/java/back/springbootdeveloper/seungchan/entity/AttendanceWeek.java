package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_TIME;
import back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

import java.time.DayOfWeek;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_week")
public class AttendanceWeek extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_week_id")
    private Long attendanceWeekId;

    @Enumerated(EnumType.STRING)
    @Column(name = "monday", length = 15, nullable = false)
    private POSSIBLE_STATUS monday = POSSIBLE_STATUS.POSSIBLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "tuesday", length = 15, nullable = false)
    private POSSIBLE_STATUS tuesday = POSSIBLE_STATUS.POSSIBLE;
    ;

    @Enumerated(EnumType.STRING)
    @Column(name = "wednesday", length = 15, nullable = false)
    private POSSIBLE_STATUS wednesday = POSSIBLE_STATUS.POSSIBLE;
    ;

    @Enumerated(EnumType.STRING)
    @Column(name = "thursday", length = 15, nullable = false)
    private POSSIBLE_STATUS thursday = POSSIBLE_STATUS.POSSIBLE;
    ;

    @Enumerated(EnumType.STRING)
    @Column(name = "friday", length = 15, nullable = false)
    private POSSIBLE_STATUS friday = POSSIBLE_STATUS.POSSIBLE;
    ;

    @Enumerated(EnumType.STRING)
    @Column(name = "saturday", length = 15, nullable = false)
    private POSSIBLE_STATUS saturday = POSSIBLE_STATUS.POSSIBLE;
    ;

    @Enumerated(EnumType.STRING)
    @Column(name = "sunday", length = 15, nullable = false)
    private POSSIBLE_STATUS sunday = POSSIBLE_STATUS.POSSIBLE;
    ;

    @OneToOne(mappedBy = "attendanceWeek")
    private ClubControl clubControl;

    public void updateMonday(POSSIBLE_STATUS monday) {
        this.monday = monday;
    }

    public void updateTuesday(POSSIBLE_STATUS tuesday) {
        this.tuesday = tuesday;
    }

    public void updateWednesday(POSSIBLE_STATUS wednesday) {
        this.wednesday = wednesday;
    }

    public void updateThursday(POSSIBLE_STATUS thursday) {
        this.thursday = thursday;
    }

    public void updateFriday(POSSIBLE_STATUS friday) {
        this.friday = friday;
    }

    public void updateSaturday(POSSIBLE_STATUS saturday) {
        this.saturday = saturday;
    }

    public void updateSunday(POSSIBLE_STATUS sunday) {
        this.sunday = sunday;
    }

    public void setClubControl(final ClubControl clubControl) {
        this.clubControl = clubControl;

        if (clubControl.getAttendanceWeek() != this) { // null 체크 추가
            clubControl.setAttendanceWeek(this);
        }
    }

    public String getStatusForDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return this.monday.getStatus();
            case TUESDAY:
                return this.tuesday.getStatus();
            case WEDNESDAY:
                return this.wednesday.getStatus();
            case THURSDAY:
                return this.thursday.getStatus();
            case FRIDAY:
                return this.friday.getStatus();
            case SATURDAY:
                return this.saturday.getStatus();
            case SUNDAY:
                return this.sunday.getStatus();
            default:
                break;
        }
        return "";
    }
}
