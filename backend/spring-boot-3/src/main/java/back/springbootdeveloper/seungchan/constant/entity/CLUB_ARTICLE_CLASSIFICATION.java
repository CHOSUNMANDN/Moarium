package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum CLUB_ARTICLE_CLASSIFICATION {
    CONFIDENTIAL("CONFIDENTIAL"),
    FREEDOM("FREEDOM"),
    SUGGESTION("SUGGESTION");

    private String sort;

    CLUB_ARTICLE_CLASSIFICATION(String sort) {
        this.sort = sort;
    }

    public boolean is(String classification) {
        return this.sort.equals(classification);
    }
}
