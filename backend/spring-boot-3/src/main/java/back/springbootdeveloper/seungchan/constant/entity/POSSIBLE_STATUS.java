package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum POSSIBLE_STATUS {
    POSSIBLE("POSSIBLE"), IMPOSSIBLE("IMPOSSIBLE");

    private String status;

    POSSIBLE_STATUS(String status) {
        this.status = status;
    }

    public Boolean isSame(String status) {
        return this.status.equals(status);
    }
}
