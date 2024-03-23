package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_SUGGESTION_CHECK;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_grade")
public class ClubGrade extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_grade_id")
  private Long clubGradeId;

  @Enumerated(EnumType.STRING)
  @Column(name = "club_grade", length = 15)
  private CLUB_GRADE clubGrade;

  /**
   * ClubGrade를 생성하는 빌더입니다.
   *
   * @param clubGrade 클럽 등급
   */
  @Builder
  public ClubGrade(CLUB_GRADE clubGrade) {
    this.clubGrade = clubGrade;
  }

  /**
   * 클럽 등급을 문자열로 반환하는 메서드입니다.
   *
   * @return 클럽 등급 문자열
   */
  public String getClubGradeString() {
    return this.clubGrade.getGrade();
  }

}
