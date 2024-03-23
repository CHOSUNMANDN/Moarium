package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.entity.AttendanceState;
import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.AttendanceStateRepository;
import back.springbootdeveloper.seungchan.repository.AttendanceWeekDateRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceWeekDateService {

  private final ClubMemberRepository clubMemberRepository;
  private final AttendanceStateRepository attendanceStateRepository;
  private final AttendanceWeekDateRepository attendanceWeekDateRepository;

  /**
   * 클럽 멤버의 오늘의 출석 주차 출석상태를 업데이트합니다.
   *
   * @param clubMemberId   출석 주차 날짜를 업데이트할 클럽 멤버의 ID
   * @param attendanceEnum 업데이트할 출석 상태
   * @throws EntityNotFoundException 지정된 ID에 해당하는 클럽 멤버나 출석 상태가 없을 때 발생하는 예외
   */
  @Transactional
  public void updateTodayAttendanceWeekDate(Long clubMemberId, ATTENDANCE_STATE attendanceEnum) {

    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    AttendanceState attendanceState = attendanceStateRepository.findById(
        clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
    // 출석 정보의 마지막 컬럼 가져오기
    AttendanceWeekDate attendanceWeekDate = getLastAttendanceWeekDate(attendanceState);

    // 원하는 출석 상태에 의해 변경
    updateAttendanceStateWithAttendanceEnum(attendanceEnum, attendanceWeekDate);

    // 업데이트 적용
    attendanceWeekDateRepository.save(attendanceWeekDate);
  }

  /**
   * 주어진 출석 상태에 따라 출석 주차 날짜를 업데이트합니다.
   *
   * @param attendanceEnum     업데이트할 출석 상태
   * @param attendanceWeekDate 출석 주차 날짜 객체
   */
  private void updateAttendanceStateWithAttendanceEnum(ATTENDANCE_STATE attendanceEnum,
      AttendanceWeekDate attendanceWeekDate) {
    if (attendanceEnum == ATTENDANCE_STATE.VACATION) {
      attendanceWeekDate.updateVacationAtToday();
    }
    if (attendanceEnum == ATTENDANCE_STATE.ABSENCE) {
      attendanceWeekDate.updateAbsenceAtToday();
    }
    if (attendanceEnum == ATTENDANCE_STATE.ATTENDANCE) {
      attendanceWeekDate.updateAttendanceAtToday();
    }
  }

  /**
   * 출석 상태를 업데이트할 수 있는지 여부를 확인합니다.
   *
   * @param clubId   클럽 ID
   * @param memberId 회원 ID
   * @return 출석 상태를 업데이트할 수 있는지 여부
   * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
   */
  public Boolean isPossibleUpdateAttendanceState(Long clubId, Long memberId) {
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
        .orElseThrow(EntityNotFoundException::new);
    final AttendanceState attendanceState = attendanceStateRepository.findById(
        clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);

    // 리스트의 마지막 요소을 반환
    AttendanceWeekDate attendanceWeekDate = getLastAttendanceWeekDate(attendanceState);

    return attendanceWeekDate.isPossibleUpdateAttendanceState();
  }

  /**
   * 특정 클럽 멤버의 최근 출석 주간 날짜를 반환합니다.
   *
   * @param clubId   클럽 식별자
   * @param memberId 멤버 식별자
   * @return 특정 클럽 멤버의 최근 출석 주간 날짜
   * @throws EntityNotFoundException 만약 주어진 clubId 또는 memberId에 해당하는 클럽 멤버가 존재하지 않을 경우 발생합니다.
   */
  public AttendanceWeekDate getLast(Long clubId, Long memberId) {
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
        .orElseThrow(EntityNotFoundException::new);
    final AttendanceState attendanceState = attendanceStateRepository.findById(
        clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);

    return getLastAttendanceWeekDate(attendanceState);
  }

  /**
   * 주어진 출석 주간 날짜 목록에서 마지막 주간 날짜를 반환합니다.
   *
   * @param attendanceState 출석 주간 날짜 목록
   * @return 마지막 주간 날짜
   */
  private AttendanceWeekDate getLastAttendanceWeekDate(AttendanceState attendanceState) {
    List<AttendanceWeekDate> attendanceWeekDates = attendanceState.getAttendanceWeekDates();
    int lastIndex = attendanceWeekDates.size() - 1; // 리스트의 마지막 요소의 인덱스를 계산합니다

    return attendanceWeekDates.get(lastIndex); // 리스트의 마지막 요소를 가져옵니다
  }
}
