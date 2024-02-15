package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "first_name", length = 10, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "nick_name", length = 30, nullable = false)
    private String nickName;

    @Column(name = "email", length = 40, nullable = false, unique = true)
    private String email;

    @Column(name = "major", length = 20, nullable = false)
    private String major;

    @Column(name = "student_id", length = 15, nullable = false)
    private String studentId;

    @Column(name = "registration", length = 10, nullable = false)
    private String registration;

    @Builder
    public Member(String firstName, String lastName, String nickName, String email, String major, String studentId, String registration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.major = major;
        this.studentId = studentId;
        this.registration = registration;
    }

    @PrePersist
    protected void onCreate() {
        // https://www.daleseo.com/java8-zoned-date-time/
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
        this.registration = String.valueOf(zonedDateTime.toLocalDate().getYear());
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateMajor(String major) {
        this.major = major;
    }

    public void updateStudentId(String studentId) {
        this.studentId = studentId;
    }
}
