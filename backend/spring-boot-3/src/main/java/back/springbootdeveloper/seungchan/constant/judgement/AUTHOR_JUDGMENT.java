package back.springbootdeveloper.seungchan.constant.judgement;

import lombok.Getter;

@Getter
public enum AUTHOR_JUDGMENT {
    AUTHOR("AUTHOR"),
    NOT_AUTHOR("NOT_AUTHOR");

    private String judgment;

    AUTHOR_JUDGMENT(String judgment) {
        this.judgment = judgment;
    }
}
