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

    @Builder
    public ClubGrade(CLUB_GRADE clubGrade) {
        this.clubGrade = clubGrade;
    }
}
