package back.springbootdeveloper.seungchan.repository;

import static back.springbootdeveloper.seungchan.entity.QClubArticleComment.clubArticleComment;

import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import com.querydsl.core.util.CollectionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
public class ClubArticleCommentRepositoryImpl implements ClubArticleCommentCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ClubArticleComment> findAllByQueryDsl() {
    return queryFactory
        .selectFrom(clubArticleComment)
        .orderBy(clubArticleComment.clubArticleCommentId.desc())
        .fetch();
  }

  @Override
  public List<ClubArticleComment> findAllByQueryDslCoveringIndex() {
    // 1) 커버링 인덱스로 대상 조회
    List<Long> ids = queryFactory
        .select(clubArticleComment.clubArticleCommentId)
        .from(clubArticleComment)
        .fetch();

    return queryFactory
        .select(clubArticleComment)
        .from(clubArticleComment)
        .where(clubArticleComment.clubArticleCommentId.in(ids))
        .orderBy(clubArticleComment.clubArticleCommentId.desc())
        .fetch(); // where in id만 있어 결과 정렬이 보장되지 않는다.
  }
}