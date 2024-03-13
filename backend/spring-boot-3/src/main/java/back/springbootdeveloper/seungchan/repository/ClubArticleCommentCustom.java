package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubArticleCommentCustom {

  List<ClubArticleComment> findAllByQueryDsl();

  List<ClubArticleComment> findAllByQueryDslCoveringIndex();
}