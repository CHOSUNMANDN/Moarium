package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestions, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Suggestions s SET s.isCheck = :updateCheck WHERE s.id = :id")
    void updateByIdCheck(@Param("id") Long id, @Param("updateCheck") Boolean updateCheck);
}
