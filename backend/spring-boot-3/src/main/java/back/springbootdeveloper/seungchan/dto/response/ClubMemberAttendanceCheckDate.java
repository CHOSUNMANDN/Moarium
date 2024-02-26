package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.AttendanceWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClubMemberAttendanceCheckDate {
    private String mondayCheck;
    private String tuesdayCheck;
    private String wednesdayCheck;
    private String thursdayCheck;
    private String fridayCheck;
    private String saturdayCheck;
    private String sundayCheck;

    public ClubMemberAttendanceCheckDate(AttendanceWeek attendanceWeek) {
        this.mondayCheck = attendanceWeek.getMonday().getStatus();
        this.tuesdayCheck = attendanceWeek.getTuesday().getStatus();
        this.wednesdayCheck = attendanceWeek.getWednesday().getStatus();
        this.thursdayCheck = attendanceWeek.getThursday().getStatus();
        this.fridayCheck = attendanceWeek.getFriday().getStatus();
        this.saturdayCheck = attendanceWeek.getSaturday().getStatus();
        this.sundayCheck = attendanceWeek.getSunday().getStatus();
    }
}
