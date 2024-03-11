package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TempMembersInformation {

  private Long clubMemberId;
  private String name;
  private String email;
  private String major;
  private String studentId;

  public TempMembersInformation(final Long clubMemberId, final Member member) {
    this.clubMemberId = clubMemberId;
    this.name = member.getFullName();
    this.email = member.getEmail();
    this.major = member.getMajor();
    this.studentId = member.getStudentId();
  }
}
