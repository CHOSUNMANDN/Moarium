package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS;
import back.springbootdeveloper.seungchan.dto.response.AttendanceStates;
import back.springbootdeveloper.seungchan.dto.response.MyAllClubMembersAttendance;
import back.springbootdeveloper.seungchan.dto.response.MyAttendanceCount;
import back.springbootdeveloper.seungchan.dto.response.MyAttendanceState;
import back.springbootdeveloper.seungchan.dto.response.MyClubMembersAttendance;
import back.springbootdeveloper.seungchan.dto.response.MyPageClubMemberInformationResDto;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

  private final ClubMemberRepository clubMemberRepository;
  private final ClubRepository clubRepository;
  private final VacationTokenRepository vacationTokenRepository;
  private final AttendanceStateRepository attendanceStateRepository;
  private final AttendanceWeekDateRepository attendanceWeekDateRepository;
  private final AttendanceWeekRepository attendanceWeekRepository;
  private final ClubControlRepository clubControlRepository;
  private final MemberRepository memberRepository;


  /**
   * 특정 회원의 모든 클럽 멤버의 출석 정보를 가져와서 리스트로 반환합니다.
   *
   * @param memberId     조회할 회원의 ID
   * @param clubMemberId 클럽 멤버의 ID
   * @return 모든 클럽 멤버의 출석 정보 목록
   * @throws EntityNotFoundException 요청한 엔티티가 존재하지 않을 경우 발생하는 예외
   */
  public List<MyAllClubMembersAttendance> getMyAllClubMembersAttendance(Long memberId,
      Long clubMemberId) {
    List<MyAllClubMembersAttendance> myAllClubMembersAttendances = new ArrayList<>();
    List<ClubMember> clubMembers = clubMemberRepository.findAllByMemberId(memberId);

    // 각 클럽 멤버에 대해 반복하여 출석 정보를 가져옴
    for (ClubMember clubMember : clubMembers) {
      // 클럽 멤버에 해당하는 클럽 정보를 조회
      Club club = clubRepository.findById(clubMember.getClubId())
          .orElseThrow(EntityNotFoundException::new);
      // 클럽 멤버에 해당하는 출석 정보를 조회
      AttendanceState attendanceState = attendanceStateRepository.findById(
          clubMember.getClubMemberId()).orElseThrow(EntityNotFoundException::new);
      // 출석 정보에 대한 휴가 토큰을 가져옴
      VacationToken vacationToken = getLastVacationToken(attendanceState);
      // 출석 주차를 역순으로 정렬하여 최근 정보를 가져옴
      List<AttendanceWeekDate> attendanceWeekDates = getLastAttendanceWeekDate(attendanceState);

      MyAttendanceCount myAttendanceCount = getMyAttendanceCount(club, attendanceWeekDates);

      // 클럽 이름과 휴가 토큰 정보를 가져옴
      String clubName = club.getClubName();
      Integer vacationTokenCount = vacationToken.getVacationToken();
      // 최근 출석 상태와 출석 횟수 정보를 객체로 생성
      MyAttendanceState myAttendanceState = new MyAttendanceState(attendanceWeekDates.get(0));

      // 클럽 멤버의 출석 정보를 객체로 생성하여 리스트에 추가
      myAllClubMembersAttendances.add(
          MyAllClubMembersAttendance.builder()
              .clubName(clubName)
              .vacationToken(vacationTokenCount)
              .myAttendanceState(myAttendanceState)
              .myAttendanceCount(myAttendanceCount)
              .build());
    }

    return myAllClubMembersAttendances;
  }

  /**
   * 주어진 클럽 멤버 ID에 해당하는 내 페이지 클럽 멤버 정보 DTO를 반환합니다.
   *
   * @param clubMemberId 클럽 멤버 ID
   * @return 내 페이지 클럽 멤버 정보 DTO
   * @throws EntityNotFoundException entity를 찾을 수 없는 경우 예외가 발생합니다.
   */
  public MyPageClubMemberInformationResDto getMyPageClubMemberInformationResDto(Long clubMemberId) {
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    Club club = clubRepository.findById(clubMember.getClubId())
        .orElseThrow(EntityNotFoundException::new);
    Member member = memberRepository.findById(clubMember.getMemberId())
        .orElseThrow(EntityNotFoundException::new);

    return MyPageClubMemberInformationResDto.builder()
        // TODO: 2/26/24 향후 디비수정
        .memberProfile("향후 디비수정")
        .memberName(member.getFullName())
        .memberStudentId(member.getStudentId())
        .ClubName(club.getClubName())
        // TODO: 2/26/24 향후 디비수정
        .memberStatusMessage("향후 디비수정")
        .build();
  }

  /**
   * 클럽 멤버의 출석 정보를 가져옵니다.
   *
   * @param clubMemberId 클럽 멤버 ID
   * @return 클럽 멤버의 출석 정보 목록
   * @throws EntityNotFoundException 클럽 멤버, 클럽 또는 출석 상태를 찾을 수 없는 경우 예외가 발생합니다.
   */
  public List<MyClubMembersAttendance> getMyClubMembersAttendance(final Long clubMemberId) {
    // 필요한 객체 설정
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    Club club = clubRepository.findById(clubMember.getClubId())
        .orElseThrow(EntityNotFoundException::new);
    AttendanceState attendanceState = attendanceStateRepository.findById(
            clubMember.getAttendanceStateId())
        .orElseThrow(EntityNotFoundException::new);
    List<AttendanceWeekDate> attendanceWeekDates = attendanceState.getAttendanceWeekDates();
    List<MyClubMembersAttendance> myClubMembersAttendances = new ArrayList<>();

    // TODO: 3/5/24 페이징 관리
    for (final AttendanceWeekDate attendanceWeekDate : attendanceWeekDates) {
      AttendanceStates attendanceStates = AttendanceStates.builder()
          .attendanceWeekDate(attendanceWeekDate)
          .build();

      MyAttendanceCount myAttendanceCount = getMyAttendanceCountOneWeek(
          club, attendanceWeekDate);

      myClubMembersAttendances.add(
          MyClubMembersAttendance.builder()
              .attendanceStates(attendanceStates)
              .myAttendanceCount(myAttendanceCount)
              .build()
      );
    }

    return myClubMembersAttendances;
  }

  /**
   * 한 주 동안의 출석 정보의 관한 Count을 가져온다.
   *
   * @param club               클럽
   * @param attendanceWeekDate 출석 주차 날짜
   * @return 한 주 동안의 출석 정보
   */
  private MyAttendanceCount getMyAttendanceCountOneWeek(final Club club,
      final AttendanceWeekDate attendanceWeekDate) {
    String attendanceCount = getCountOfAttendanceWeekDate(attendanceWeekDate,
        ATTENDANCE_STATE.ATTENDANCE, club);
    String vacationCount = getCountOfAttendanceWeekDate(attendanceWeekDate,
        ATTENDANCE_STATE.VACATION, club);
    String absenceCount = getCountOfAttendanceWeekDate(attendanceWeekDate,
        ATTENDANCE_STATE.ABSENCE, club);
    String totalCount = getTotalCount(attendanceCount, vacationCount, absenceCount);

    return MyAttendanceCount.builder()
        .attendance(attendanceCount)
        .vacation(vacationCount)
        .absence(absenceCount)
        .totalCount(totalCount)
        .build();
  }


  /**
   * 주어진 출석 상태를 갖는 출석 주차 목록에서 마지막 휴가 토큰을 가져옵니다.
   *
   * @param attendanceState 출석 상태
   * @return 마지막 휴가 토큰
   */
  private VacationToken getLastVacationToken(final AttendanceState attendanceState) {
    List<VacationToken> vacationTokens = attendanceState.getVacationTokens();
    Integer lastIndex = vacationTokens.size() - 1;
    VacationToken vacationToken = vacationTokens.get(lastIndex);
    return vacationToken;
  }

  /**
   * 클럽과 출석 주차 목록을 사용하여 내 출석 횟수를 가져옵니다.
   *
   * @param club                클럽
   * @param attendanceWeekDates 출석 주차 목록
   * @return 내 출석 횟수 정보
   */
  private MyAttendanceCount getMyAttendanceCount(Club club,
      List<AttendanceWeekDate> attendanceWeekDates) {
    // 각 출석 상태에 따른 출석 횟수를 가져옴
    String attendanceCount = getCountOfAttendanceWeekDates(attendanceWeekDates,
        ATTENDANCE_STATE.ATTENDANCE, club);
    String vacationCount = getCountOfAttendanceWeekDates(attendanceWeekDates,
        ATTENDANCE_STATE.VACATION, club);
    String absenceCount = getCountOfAttendanceWeekDates(attendanceWeekDates,
        ATTENDANCE_STATE.ABSENCE, club);
    // 총 출석 횟수 계산
    String totalCount = getTotalCount(attendanceCount, vacationCount, absenceCount);

    return MyAttendanceCount.builder()
        .attendance(attendanceCount)
        .vacation(vacationCount)
        .absence(absenceCount)
        .totalCount(totalCount)
        .build();
  }

  /**
   * 출석, 휴가, 결석 횟수를 받아서 총 출석 횟수를 계산합니다.
   *
   * @param attendanceCount 출석 횟수
   * @param vacationCount   휴가 횟수
   * @param absenceCount    결석 횟수
   * @return 총 출석 횟수를 나타내는 문자열
   */
  private String getTotalCount(String attendanceCount, String vacationCount, String absenceCount) {
    Integer totalCount =
        Integer.valueOf(attendanceCount) + Integer.valueOf(vacationCount) + Integer.valueOf(
            absenceCount);

    return String.valueOf(totalCount);
  }

  /**
   * 주어진 출석 주(AttendanceWeek)에 대해 주어진 클럽(Club)의 출석 주차에 대한 출석 상태(ATTENDANCE_STATE)를 얻어옵니다.
   *
   * @param attendanceWeekDates 출석 주차(AttendanceWeekDate) 목록
   * @param attendanceState     출석 상태
   * @param club                출석을 검사할 클럽
   * @return 해당 출석 주차에 대한 주어진 출석 상태의 출석 횟수
   */
  private String getCountOfAttendanceWeekDates(List<AttendanceWeekDate> attendanceWeekDates,
      ATTENDANCE_STATE attendanceState, Club club) {
    ClubControl clubControl = club.getClubControl();
    AttendanceWeek attendanceWeek = clubControl.getAttendanceWeek();
    Integer count = 0;

    // 각 출석 주차에 대해 반복
    for (AttendanceWeekDate attendanceWeekDate : attendanceWeekDates) {
      // 요일에 대한 반복
      for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
        // 주어진 요일에 대한 출석 상태 및 가능한 출석 상태 비교
        if (attendanceWeekDate.getAttendanceStateForDay(dayOfWeek).is(attendanceState) &&
            POSSIBLE_STATUS.POSSIBLE.is(attendanceWeek.getStatusForDay(dayOfWeek))) {
          count++;
        }
      }
    }

    return String.valueOf(count);
  }

  /**
   * 주어진 출석 주차 날짜에 대한 특정 출석 상태의 출석 횟수를 가져옵니다.
   *
   * @param attendanceWeekDate 출석 주차 날짜
   * @param attendanceState    출석 상태
   * @param club               클럽
   * @return 출석 횟수
   */
  private String getCountOfAttendanceWeekDate(AttendanceWeekDate attendanceWeekDate,
      ATTENDANCE_STATE attendanceState, Club club) {
    ClubControl clubControl = club.getClubControl();
    AttendanceWeek attendanceWeek = clubControl.getAttendanceWeek();
    Integer count = 0;

    for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
      // 주어진 요일에 대한 출석 상태 및 가능한 출석 상태 비교
      if (attendanceWeekDate.getAttendanceStateForDay(dayOfWeek).is(attendanceState) &&
          POSSIBLE_STATUS.POSSIBLE.is(attendanceWeek.getStatusForDay(dayOfWeek))) {
        count++;
      }
    }

    return String.valueOf(count);
  }


  /**
   * 주어진 출석 상태(AttendanceState)에 대한 마지막 출석 주차(AttendanceWeekDate) 목록을 반환합니다.
   *
   * @param attendanceState 출석 상태
   * @return 마지막 출석 주차 목록
   */
  private List<AttendanceWeekDate> getLastAttendanceWeekDate(AttendanceState attendanceState) {
    // 출석 상태에서 출석 주차 목록을 가져옴
    List<AttendanceWeekDate> attendanceWeekDates = attendanceState.getAttendanceWeekDates();
    // 출석 주차 목록을 역순으로 정렬하여 마지막 주차가 먼저 오도록 함
    Collections.reverse(attendanceWeekDates);

    // 마지막 출석 주차 목록 반환
    return attendanceWeekDates;
  }
}
