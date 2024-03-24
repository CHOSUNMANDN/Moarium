package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceCheckTime;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberInformationRepository extends
    JpaRepository<ClubMemberInformation, Long> {

}