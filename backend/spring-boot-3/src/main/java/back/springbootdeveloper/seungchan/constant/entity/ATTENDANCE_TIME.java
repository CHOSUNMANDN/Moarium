package back.springbootdeveloper.seungchan.constant.entity;

public enum ATTENDANCE_TIME {
    TEN("10"), ELEVEN("11"), TWELVE("12"), THIRTEEN("13"), FOURTEEN("14"),
    FIFTEEN("15"), SIXTEEN("16"), SEVENTEEN("17"), EIGHTEEN("18"), NINETEEN("19"),
    TWENTY("20"), TWENTY_ONE("21"), TWENTY_TWO("22"), TWENTY_THREE("23"), TWENTY_FOUR("24");

    private final String value;

    ATTENDANCE_TIME(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
