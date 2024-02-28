package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceCheckTime;
import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceCheckTimeRepository extends JpaRepository<AttendanceCheckTime, Long> {

}