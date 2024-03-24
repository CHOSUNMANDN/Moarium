package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubGradeRepository extends JpaRepository<ClubGrade, Long> {

  /**
   * 주어진 클럽 등급과 일치하는 클럽 등급을 찾습니다.
   *
   * @param clubGrade 클럽 등급
   * @return 주어진 클럽 등급과 일치하는 클럽 등급 (Optional)
   */
  Optional<ClubGrade> findByClubGrade(CLUB_GRADE clubGrade);
}