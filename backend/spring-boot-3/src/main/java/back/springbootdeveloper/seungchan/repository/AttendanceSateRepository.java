package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceSateRepository extends JpaRepository<AttendanceState, Long> {
}