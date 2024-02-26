package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.entity.VacationTokenControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationTokenRepository extends JpaRepository<VacationToken, Long> {

}