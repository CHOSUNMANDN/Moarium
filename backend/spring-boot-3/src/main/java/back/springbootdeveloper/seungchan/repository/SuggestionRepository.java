package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestions, Long> {
}
