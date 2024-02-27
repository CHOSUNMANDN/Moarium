package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum CLUB_ARTICLE_SUGGESTION_CHECK {
    CONFIRMED("CONFIRMED"),
    UNCONFIRMED("UNCONFIRMED");

    private String check;

    CLUB_ARTICLE_SUGGESTION_CHECK(String check) {
        this.check = check;
    }
}
