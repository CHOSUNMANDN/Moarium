package back.springbootdeveloper.seungchan.repository;

import static back.springbootdeveloper.seungchan.entity.QClubArticleComment.clubArticleComment;

import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ClubArticleCommentRepositorySupport extends QuerydslRepositorySupport {

  private final JPAQueryFactory queryFactory;

  public ClubArticleCommentRepositorySupport(JPAQueryFactory queryFactory) {
    super(ClubArticleComment.class);
    this.queryFactory = queryFactory;
  }

  public List<ClubArticleComment> findAllByMemberId(Long memberId) {
    return queryFactory
        .selectFrom(clubArticleComment)
        .where(clubArticleComment.memberId.eq(memberId))
        .fetch();
  }
}