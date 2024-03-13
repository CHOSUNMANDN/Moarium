package back.springbootdeveloper.seungchan.test;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.repository.ClubArticleCommentRepository;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;

@DataJpaTest
public class PagingTest {

  @Autowired
  private ClubArticleRepository clubArticleRepository;
  @Autowired
  private ClubArticleCommentRepository clubArticleCommentRepository;


  @Test
  void Paging_테스트() throws Exception {
    Long clubId = 1L;
    Pageable pageable = PageRequest.of(0, 6);
    Page<ClubArticle> clubArticlePage = clubArticleRepository.findAllByClubIdAndClassification(
        clubId,
        CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, pageable);

    for (final ClubArticle clubArticle : clubArticlePage) {
      System.out.println("clubArticle.getClubArticleId() = " + clubArticle.getClubArticleId());
    }
    System.out.println();

    pageable = pageable.next();
    clubArticlePage = clubArticleRepository.findAllByClubIdAndClassification(
        clubId,
        CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, pageable);
  }

  @Test
  void Paging_테스트_club_member_id() throws Exception {
    Long clubId = 1L;
    Pageable pageable = PageRequest.of(0, 6);
    Page<ClubArticle> clubArticlePage = clubArticleRepository.findAllByClubIdAndClassification(
        clubId,
        CLUB_ARTICLE_CLASSIFICATION.SUGGESTION, pageable);

    Page<ClubArticleComment> clubArticleComments = clubArticleCommentRepository.findAllByClubArticle_ClubArticleId(
        1L, pageable);

    for (final ClubArticleComment clubArticleComment : clubArticleComments) {
      System.out.println("clubArticleComment.getClubArticleCommentId() = "
          + clubArticleComment.getClubArticleCommentId());
    }
  }

  @Test
  void SELECT_CLUT_ARTICLE_COMMENT_CHECK_TIME() throws Exception {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    List<ClubArticleComment> comments = clubArticleCommentRepository.findAll();

    stopWatch.stop();
    System.out.println(stopWatch.prettyPrint());
    System.out.println("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
  }
}
