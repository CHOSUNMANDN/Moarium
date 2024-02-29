package back.springbootdeveloper.seungchan.constant.entity;

import lombok.Getter;

@Getter
public enum CLUB_GRADE {
    LEADER(1, "LEADER"), DEPUTY_LEADER(2, "DEPUTY_LEADER"),
    MEMBER(3, "MEMBER"), DORMANT(4, "DORMANT");

    private Integer id;
    private String grade;

    CLUB_GRADE(Integer id, String grade) {
        this.id = id;
        this.grade = grade;
    }

    public boolean is(CLUB_GRADE targetClubGrade) {
        return this.grade.equals(targetClubGrade.getGrade());
    }
}
