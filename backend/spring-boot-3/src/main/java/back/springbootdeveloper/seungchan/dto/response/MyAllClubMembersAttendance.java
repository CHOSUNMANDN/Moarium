package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.AttendanceState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyAllClubMembersAttendance {
    private String clubName;
    private Integer vacationToken;
    private MyAttendanceState myAttendanceState;
    private MyAttendanceCount myAttendanceCount;
}
