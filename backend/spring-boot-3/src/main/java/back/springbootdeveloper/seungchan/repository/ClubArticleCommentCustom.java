package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubArticleCommentCustom {

  List<ClubArticleComment> findAllByMemberId(Long memberId);
}