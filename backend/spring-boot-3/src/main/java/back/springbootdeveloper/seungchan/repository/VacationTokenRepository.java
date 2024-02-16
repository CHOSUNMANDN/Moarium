package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.entity.VacationTokenControl;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface VacationTokenRepository extends JpaRepository<VacationToken, Long> {

}