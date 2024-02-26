package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum CLUB_GRADE {
    LEADER(1), DEPUTY_LEADER(2),
    MEMBER(3), DORMANT(4);

    private Integer id;

    CLUB_GRADE(Integer id) {
        this.id = id;
    }
}
