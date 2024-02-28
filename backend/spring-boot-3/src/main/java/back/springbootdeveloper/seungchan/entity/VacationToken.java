package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vacation_token")
public class VacationToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_token_id")
    private Long vacationTokenId;

    @Column(name = "vacation_token")
    private Integer vacationToken = 5;

    @Column(name = "vacation_token_date", length = 15, nullable = false)
    private String vacationTokenDate;

    @OneToOne(mappedBy = "vacationToken")
    private AttendanceState attendanceSate;

    @Builder
    public VacationToken(Integer vacationCount) {
        this.vacationToken = vacationCount;
    }

    @PrePersist
    protected void onCreate() {
        // https://www.daleseo.com/java8-zoned-date-time/
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
        this.vacationTokenDate = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public void updateVacationCount(Integer vacationCount) {
        this.vacationToken = vacationCount;
    }

    public void subtractVacationCount() {
        this.vacationToken = this.vacationToken - 1;
    }

    public void subtractVacationCount(Integer number) {
        this.vacationToken = this.vacationToken - number;
    }

    public void addVacationCount() {
        this.vacationToken = this.vacationToken + 1;
    }

    public void addVacationCount(Integer number) {
        this.vacationToken = this.vacationToken + number;
    }

    public void setAttendanceSate(final AttendanceState attendanceSate) {
        this.attendanceSate = attendanceSate;

        if (attendanceSate.getVacationToken() != this) { // null 체크 추가
            attendanceSate.setVacationToken(this);
        }
    }
}
