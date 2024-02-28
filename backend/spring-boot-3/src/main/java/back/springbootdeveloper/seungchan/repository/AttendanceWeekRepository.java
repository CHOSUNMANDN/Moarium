package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.AttendanceWeek;
import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceWeekRepository extends JpaRepository<AttendanceWeek, Long> {

}