package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyClubMembersAttendance {

  private AttendanceStates attendanceStates;
  private MyAttendanceCount myAttendanceCount;
}
