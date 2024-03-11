package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_member")
public class ClubMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_member_id", nullable = false)
  private Long clubMemberId;

  @Column(name = "member_id", nullable = false)
  private Long memberId; // member

  @Column(name = "club_id", nullable = false)
  private Long clubId; // club

  @Column(name = "club_grade_id", nullable = false)
  private Long clubGradeId; // member - club

  @Column(name = "attendance_state_id", nullable = false)
  private Long attendanceStateId; // member - club

  @Column(name = "club_member_information_id", nullable = false)
  private Long clubMemberInformationId; // member - club

  @Builder
  public ClubMember(Long memberId, Long clubId, Long clubGradeId, Long attendanceSateId,
      Long clubMemberInformationId) {
    this.memberId = memberId;
    this.clubId = clubId;
    this.clubGradeId = clubGradeId;
    this.attendanceStateId = attendanceSateId;
    this.clubMemberInformationId = clubMemberInformationId;
  }

  /**
   * 클럽 등급 ID를 업데이트하는 메서드입니다.
   *
   * @param clubGradeId 새로운 클럽 등급 ID
   */
  public void updateClubGradeId(Long clubGradeId) {
    this.clubGradeId = clubGradeId;
  }

  /**
   * 클럽 등급 ID를 업데이트하는 메서드입니다. Integer 타입을 Long 타입으로 변환하여 업데이트합니다.
   *
   * @param clubGradeId 새로운 클럽 등급 ID (Integer 타입)
   */
  public void updateClubGradeId(Integer clubGradeId) {
    this.clubGradeId = Long.valueOf(clubGradeId);
  }

  /**
   * ClubMember의 클럽 등급 ID를 업데이트합니다.
   *
   * @param clubGrade 클럽 등급
   */
  public void updateClubGradeId(CLUB_GRADE clubGrade) {
    // 주어진 클럽 등급의 ID를 사용하여 ClubMember의 클럽 등급 ID를 업데이트합니다.
    this.clubGradeId = Long.valueOf(clubGrade.getId());
  }
}
