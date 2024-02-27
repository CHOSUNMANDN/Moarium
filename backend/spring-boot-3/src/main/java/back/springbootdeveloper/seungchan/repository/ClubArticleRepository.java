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
    List<ClubArticle> findAllByClubMemberId(Long clubMemberId);

    List<ClubArticle> findAllByClubMemberIdAndClassification(Long clubMemberId, CLUB_ARTICLE_CLASSIFICATION classification);
}