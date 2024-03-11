package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubArticleCommentRepository extends JpaRepository<ClubArticleComment, Long> {

  Page<ClubArticleComment> findAllByClubArticle_ClubArticleId(Long clubArticleId,
      Pageable pageable);

}