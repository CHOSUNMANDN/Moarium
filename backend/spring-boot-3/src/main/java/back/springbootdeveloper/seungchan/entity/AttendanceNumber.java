package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 모든 팀들의 회원들이 출석을 하기위한 출석 번호
 * 랜덤으로 4자리 1~9의 숫자가 생성된다.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "attendance_number")
public class AttendanceNumber extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_number_id")
    private Long attendanceNumberId;

    @Column(name = "attendance_number", length = 10, nullable = false)
    private String attendanceNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    public String getAttendanceDate() {
        return attendanceDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @PrePersist
    protected void onCreate() {
        // https://www.daleseo.com/java8-zoned-date-time/
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
        this.attendanceDate = zonedDateTime.toLocalDate();

        // 1부터 9까지 랜덤한 4자리 숫자 생성
        int randomAttendanceNumber = (int) (Math.random() * 9_000) + 1_000;
        this.attendanceNumber = String.valueOf(randomAttendanceNumber);
    }
}
