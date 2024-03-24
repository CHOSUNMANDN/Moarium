package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceWeek;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
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

  /**
   * 특정 클럽의 ID를 통해 출석 데이터를 찾습니다.
   *
   * @param clubId 출석 데이터를 찾을 클럽의 ID입니다.
   * @return 현재 주의 클럽 출석 데이터입니다.
   * @throws EntityNotFoundException 주어진 ID로 클럽을 찾을 수 없을 때 발생합니다.
   */
  public AttendanceWeek findByClubId(Long clubId) {
    final Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
    final ClubControl clubControl = club.getClubControl();
    return clubControl.getAttendanceWeek();
  }
}
