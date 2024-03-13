package back.springbootdeveloper.seungchan.repository;

import static back.springbootdeveloper.seungchan.entity.QClubArticleComment.clubArticleComment;

import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
public class ClubArticleCommentRepositoryImpl implements ClubArticleCommentCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ClubArticleComment> findAllByMemberId(final Long memberId) {
    return queryFactory
        .selectFrom(clubArticleComment)
        .where(clubArticleComment.memberId.eq(memberId))
        .fetch();
  }
}