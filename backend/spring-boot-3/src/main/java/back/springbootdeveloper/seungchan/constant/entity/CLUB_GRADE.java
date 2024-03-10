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

  /**
   * 현재 ClubGrade의 등급과 주어진 targetClubGrade의 등급을 비교하여 동일한지 여부를 반환합니다.
   *
   * @param targetClubGrade 비교할 ClubGrade 객체
   * @return 현재 ClubGrade의 등급과 주어진 targetClubGrade의 등급이 동일하면 true, 아니면 false
   */
  public boolean is(CLUB_GRADE targetClubGrade) {
    return this.grade.equals(targetClubGrade.getGrade());
  }


  /**
   * 현재 ClubGrade의 id와 주어진 targetClubGradeId를 비교하여 동일한지 여부를 반환합니다.
   *
   * @param targetClubGradeId 비교할 ClubGrade의 id
   * @return 현재 ClubGrade의 id와 주어진 targetClubGradeId가 동일하면 true, 아니면 false
   */
  public boolean is(Long targetClubGradeId) {
    return this.id.equals(targetClubGradeId.intValue());
  }
}
