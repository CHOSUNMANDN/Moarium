package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubArticleCommentRepository extends JpaRepository<ClubArticleComment, Long> {

}