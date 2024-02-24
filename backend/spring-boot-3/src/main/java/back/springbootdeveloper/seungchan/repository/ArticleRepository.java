package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Article;
import back.springbootdeveloper.seungchan.entity.AttendanceCheckTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}