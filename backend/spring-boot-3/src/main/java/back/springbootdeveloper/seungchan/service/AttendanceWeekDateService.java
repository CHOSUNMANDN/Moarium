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

        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(EntityNotFoundException::new);
        AttendanceState attendanceState = attendanceStateRepository.findById(clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
        AttendanceWeekDate attendanceWeekDate = attendanceState.getAttendanceWeekDate();

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
    private void updateAttendanceStateWithAttendanceEnum(ATTENDANCE_STATE attendanceEnum, AttendanceWeekDate attendanceWeekDate) {
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
}
