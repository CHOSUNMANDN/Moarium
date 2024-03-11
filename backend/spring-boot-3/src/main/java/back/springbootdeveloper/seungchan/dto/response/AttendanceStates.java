package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.AttendanceState;
import back.springbootdeveloper.seungchan.entity.AttendanceWeek;
import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AttendanceStates {

  private String monday;
  private String tuesday;
  private String wednesday;
  private String thursday;
  private String friday;
  private String saturday;
  private String sunday;

  @Builder
  public AttendanceStates(AttendanceWeekDate attendanceWeekDate) {
    this.monday = attendanceWeekDate.getMonday().getState();
    this.tuesday = attendanceWeekDate.getTuesday().getState();
    this.wednesday = attendanceWeekDate.getWednesday().getState();
    this.thursday = attendanceWeekDate.getThursday().getState();
    this.friday = attendanceWeekDate.getFriday().getState();
    this.saturday = attendanceWeekDate.getSaturday().getState();
    this.sunday = attendanceWeekDate.getSunday().getState();
  }
}
