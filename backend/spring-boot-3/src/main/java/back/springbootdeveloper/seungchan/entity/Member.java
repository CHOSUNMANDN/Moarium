package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "first_name", length = 10)
  private String firstName;

  @Column(name = "last_name", length = 20)
  private String lastName;

  @Column(name = "nick_name", length = 30)
  private String nickName;

  @Column(name = "email", length = 40, nullable = false, unique = true)
  private String email;

  @Column(name = "major", length = 20)
  private String major;

  @Column(name = "student_id", length = 15)
  private String studentId;

  @Column(name = "registration", length = 10)
  private String registration;

  @Builder
  public Member(String firstName, String lastName, String nickName, String email, String major,
      String studentId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.nickName = nickName;
    this.email = email;
    this.major = major;
    this.studentId = studentId;
  }

  @Builder
  public Member(String email) {
    this.email = email;
  }

  @PrePersist
  protected void onCreate() {
    // https://www.daleseo.com/java8-zoned-date-time/
    LocalDateTime dateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul"));
    this.registration = String.valueOf(zonedDateTime.toLocalDate().getYear());
  }

  /**
   * 전체 이름을 반환하는 메서드입니다.
   *
   * @return 전체 이름
   */
  public String getFullName() {
    return this.firstName + this.lastName;
  }

  /**
   * 이름을 업데이트하는 메서드입니다.
   *
   * @param firstName 새로운 이름
   */
  public void updateFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * 성을 업데이트하는 메서드입니다.
   *
   * @param lastName 새로운 성
   */
  public void updateLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * 닉네임을 업데이트하는 메서드입니다.
   *
   * @param nickName 새로운 닉네임
   */
  public void updateNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * 이메일을 업데이트하는 메서드입니다.
   *
   * @param email 새로운 이메일
   */
  public void updateEmail(String email) {
    this.email = email;
  }

  /**
   * 전공을 업데이트하는 메서드입니다.
   *
   * @param major 새로운 전공
   */
  public void updateMajor(String major) {
    this.major = major;
  }

  /**
   * 학생 ID를 업데이트하는 메서드입니다.
   *
   * @param studentId 새로운 학생 ID
   */
  public void updateStudentId(String studentId) {
    this.studentId = studentId;
  }


  /**
   * 두 개의 식별자가 동일한지 여부를 확인합니다.
   *
   * @param targetId target 식별자
   * @return 두 식별자가 동일하면 true, 그렇지 않으면 false를 반환합니다.
   */
  public Boolean isSame(Long targetId) {
    // 두 개의 식별자가 동일한지 비교하여 결과를 반환합니다.
    return memberId.equals(targetId);
  }
}
