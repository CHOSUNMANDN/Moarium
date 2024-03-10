package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClubMemberResponse {

  private Long clubMemberId;
  private String memberName;
  private String vacationToken;
  private AttendanceStates attendanceStatus;

  @Builder
  public ClubMemberResponse(Long clubMemberId, String memberName, Integer vacationToken,
      AttendanceStates attendanceStatus) {
    this.clubMemberId = clubMemberId;
    this.memberName = memberName;
    this.vacationToken = String.valueOf(vacationToken);
    this.attendanceStatus = attendanceStatus;
  }
}
