package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubArticleRepository extends JpaRepository<ClubArticle, Long> {

  /**
   * 특정 클럽 회원이 작성한 모든 클럽 게시물을 찾습니다.
   *
   * @param clubMemberId 클럽 회원의 ID
   * @return 지정된 클럽 회원이 작성한 클럽 게시물 목록
   */
  List<ClubArticle> findAllByClubMemberId(Long clubMemberId);

  /**
   * 특정 클럽 회원이 작성한 특정 분류를 가진 모든 클럽 게시물을 찾습니다.
   *
   * @param clubMemberId   클럽 회원의 ID
   * @param classification 필터링할 클럽 게시물의 분류
   * @return 지정된 클럽 회원이 작성하고 지정된 분류를 가진 클럽 게시물 목록
   */
  List<ClubArticle> findAllByClubMemberIdAndClassification(Long clubMemberId,
      CLUB_ARTICLE_CLASSIFICATION classification);
}