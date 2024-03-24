package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TempMembersDetailInformationResDto {

  private Long clubMemberId;
  private String name;
  private String email;
  private String major;
  private String studentId;
  private List<CustomInformation> customInformations;

  public TempMembersDetailInformationResDto(final Long clubMemberId, final Member member,
      final List<CustomInformation> customInformations) {
    this.clubMemberId = clubMemberId;
    this.name = member.getFullName();
    this.email = member.getEmail();
    this.major = member.getMajor();
    this.studentId = member.getStudentId();
    this.customInformations = customInformations;
  }
}
