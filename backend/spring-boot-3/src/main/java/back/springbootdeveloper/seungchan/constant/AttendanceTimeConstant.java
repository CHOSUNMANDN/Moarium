package back.springbootdeveloper.seungchan.constant;

import lombok.Getter;

@Getter
public enum AttendanceTimeConstant {
  TIME_09("09"),
  TIME_10("10"),
  TIME_11("11"),
  TIME_12("12"),
  TIME_13("13"),
  TIME_14("14"),
  TIME_15("15"),
  TIME_16("16"),
  TIME_17("17"),
  TIME_18("18"),
  TIME_19("19"),
  TIME_20("20"),
  TIME_21("21"),
  TIME_22("22"),
  TIME_23("23"),
  TIME_24("24");

  private final String time;

  AttendanceTimeConstant(String time) {
    this.time = time;
  }
}
