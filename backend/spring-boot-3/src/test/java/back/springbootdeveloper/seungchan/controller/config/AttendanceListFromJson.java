package back.springbootdeveloper.seungchan.controller.config;

import java.util.List;

public class AttendanceListFromJson {

  private List<String> absences;
  private List<String> beforeVacationDate;
  private List<String> preVacationDate;

  public List<String> getAbsences() {
    return absences;
  }

  public List<String> getBeforeVacationDate() {
    return beforeVacationDate;
  }

  public List<String> getPreVacationDate() {
    return preVacationDate;
  }
}