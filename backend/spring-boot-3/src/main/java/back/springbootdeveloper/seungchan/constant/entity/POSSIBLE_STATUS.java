package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum POSSIBLE_STATUS {
    POSSIBLE("POSSIBLE"), IMPOSSIBLE("IMPOSSIBLE");

    private String status;

    POSSIBLE_STATUS(String status) {
        this.status = status;
    }

    /**
     * 현재 상태가 주어진 상태와 일치하는지 확인합니다.
     *
     * @param status 비교할 상태
     * @return 현재 상태가 주어진 상태와 일치하면 true, 그렇지 않으면 false를 반환합니다.
     */
    public Boolean is(String status) {
        return this.status.equals(status);
    }

    /**
     * 현재 상태가 주어진 상태와 일치하지 않는지 확인합니다.
     *
     * @param status 비교할 상태
     * @return 현재 상태가 주어진 상태와 일치하지 않으면 true, 그렇지 않으면 false를 반환합니다.
     */
    public boolean isNot(String status) {
        return !this.status.equals(status);
    }
}
