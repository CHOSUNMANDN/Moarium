package back.springbootdeveloper.seungchan.entity;

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
    public ClubMember(Long memberId, Long clubId, Long clubGradeId, Long attendanceSateId, Long clubMemberInformationId) {
        this.memberId = memberId;
        this.clubId = clubId;
        this.clubGradeId = clubGradeId;
        this.attendanceStateId = attendanceSateId;
        this.clubMemberInformationId = clubMemberInformationId;
    }

    public void updateClubGradeId(Long clubGradeId) {
        this.clubGradeId = clubGradeId;
    }
}
