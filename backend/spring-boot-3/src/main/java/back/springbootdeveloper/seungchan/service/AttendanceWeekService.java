package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS;
import back.springbootdeveloper.seungchan.entity.AttendanceWeek;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.AttendanceWeekRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceWeekService {
    private final ClubRepository clubRepository;

    /**
     * 특정 클럽에서 오늘의 가능한 상태를 가져옵니다.
     *
     * @param clubId         클럽 식별자
     * @param todayDayOfWeek 오늘의 요일
     * @return 오늘의 가능한 상태
     * @throws EntityNotFoundException 만약 주어진 clubId에 해당하는 클럽이 존재하지 않을 경우 발생합니다.
     */
    public String getTodayPossibleStatus(Long clubId, DayOfWeek todayDayOfWeek) {
        Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
        ClubControl clubControl = club.getClubControl();
        AttendanceWeek attendanceWeek = clubControl.getAttendanceWeek();

        return attendanceWeek.getStatusForDay(todayDayOfWeek);
    }
}
