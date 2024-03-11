package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomClubApplyInformationRepository extends
    JpaRepository<CustomClubApplyInformation, Long> {

}
