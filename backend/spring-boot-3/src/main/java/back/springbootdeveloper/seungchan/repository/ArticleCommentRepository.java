package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Article;
import back.springbootdeveloper.seungchan.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

}