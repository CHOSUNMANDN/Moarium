package back.springbootdeveloper.seungchan.test;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class PagingTest {

  @Autowired
  private ClubArticleRepository clubArticleRepository;


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
}
