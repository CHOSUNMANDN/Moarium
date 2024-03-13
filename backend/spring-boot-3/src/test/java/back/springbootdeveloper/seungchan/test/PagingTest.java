package back.springbootdeveloper.seungchan.test;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.repository.ClubArticleCommentRepository;
import back.springbootdeveloper.seungchan.repository.ClubArticleCommentRepositorySupport;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;

@SpringBootTest
public class PagingTest {

  @Autowired
  private ClubArticleRepository clubArticleRepository;
  @Autowired
  private ClubArticleCommentRepository clubArticleCommentRepository;
  @Autowired
  private ClubArticleCommentRepositorySupport clubArticleCommentRepositorySupport;

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
    List<ClubArticleComment> clubArticleComments = clubArticleCommentRepositorySupport.findAllByMemberId(
        1L);
    System.out.println("clubArticleComments.size() = " + clubArticleComments.size());
    System.out.println(stopWatch.prettyPrint());
    System.out.println("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
  }

  @Test
  void SELECT_CLUT_ARTICLE_COMMENT_CHECK_TIME_2() throws Exception {
    for (int i = 0; i < 10; i++) {
      long beforeTime = System.currentTimeMillis(); // 코드 실행 전에 시간 받아오기
      List<ClubArticleComment> clubArticleComments = clubArticleCommentRepository.findAll();

      long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
      double secDiffTime = (afterTime - beforeTime) / 1000.0; // 두 시간에 차 계산 후 초로 변환
      System.out.println("시간차이(s) : " + secDiffTime);
    }
  }

  @Test
  void SELECT_CLUT_ARTICLE_COMMENT_CHECK_TIME_3() throws Exception {
    for (int i = 0; i < 10; i++) {
      long beforeTime = System.currentTimeMillis(); // 코드 실행 전에 시간 받아오기

      List<ClubArticleComment> clubArticleComments = clubArticleCommentRepository.findAllByQueryDsl();

      long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
      double secDiffTime = (afterTime - beforeTime) / 1000.0; // 두 시간에 차 계산 후 초로 변환
      System.out.println("시간차이(s) : " + secDiffTime);
    }
  }

  @Test
  void SELECT_CLUT_ARTICLE_COMMENT_CHECK_TIME_4() throws Exception {
    for (int i = 0; i < 10; i++) {
      long beforeTime = System.currentTimeMillis(); // 코드 실행 전에 시간 받아오기

      List<ClubArticleComment> clubArticleComments = clubArticleCommentRepository.findAllByQueryDslCoveringIndex();

      long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
      double secDiffTime = (afterTime - beforeTime) / 1000.0; // 두 시간에 차 계산 후 초로 변환
      System.out.println("시간차이(s) : " + secDiffTime);
    }
  }
}
