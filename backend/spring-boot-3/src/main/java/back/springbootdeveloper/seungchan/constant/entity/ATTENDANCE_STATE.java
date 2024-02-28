package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum ATTENDANCE_STATE {
    UNDECIDED("UNDECIDED"),
    ATTENDANCE("ATTENDANCE"),
    ABSENCE("ABSENCE"),
    VACATION("VACATION");

    private String state;

    ATTENDANCE_STATE(String state) {
        this.state = state;
    }

    public boolean is(ATTENDANCE_STATE attendanceState) {
        return this.state.equals(attendanceState.getState());
    }
}
