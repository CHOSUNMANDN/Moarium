package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubDetailPageService {

  private final ClubMemberRepository clubMemberRepository;
  private final MemberRepository memberRepository;
  private final VacationTokenRepository vacationTokenRepository;
  private final AttendanceStateRepository attendanceStateRepository;
  private final AttendanceWeekDateRepository attendanceWeekDateRepository;
  private final ClubRepository clubRepository;
  private final ClubMemberInformationRepository clubMemberInformationRepository;
  private final AttendanceWeekRepository attendanceWeekRepository;

  /**
   * 주어진 클럽의 휴면 회원들의 전체 이름을 가져와서 반환합니다.
   *
   * @param clubId 클럽의 고유 ID
   * @return 휴면 회원들의 전체 이름으로 이루어진 문자열 리스트
   */
  public List<String> getAllDormancyMemberNamesOfClub(Long clubId) {
    List<ClubMember> dormantClubMembers = this.clubMemberRepository.findAllByClubIdAndClubGradeId(
        clubId, CLUB_GRADE.DORMANT.getId());
    List<Member> dormantMembers = getMembersFromClubMember(dormantClubMembers);

    return getFullNamesFromMembers(dormantMembers);
  }

  /**
   * 특정 클럽의 특정 회원에 대한 클럽 회원 상세 정보를 반환합니다.
   *
   * @param clubId      클럽의 ID
   * @param memberId    회원의 ID
   * @param myClubGrade 나의 클럽에서의 등급
   * @return 클럽 회원 상세 정보를 담은 ClubMemberDetailResDto 객체
   * @throws EntityNotFoundException 클럽이나 회원을 찾을 수 없는 경우
   */
  public ClubMemberDetailResDto getClubMemberResponse(Long clubId, Long memberId,
      CLUB_GRADE myClubGrade) {
    List<ClubMember> clubMembers = clubMemberRepository.findAllByClubIdExcludeDormantAndTempMember(
        clubId,
        CLUB_GRADE.DORMANT.getId(), CLUB_GRADE.TEMP_MEMBER.getId());
    // ClubMemberResponse 리스트 생성
    List<ClubMemberResponse> clubMemberResponses = getClubMemberResponsesFromClubMembers(
        clubMembers);
    Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
    ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
        .orElseThrow(EntityNotFoundException::new);
    ClubControl clubControl = club.getClubControl();
    ClubMemberAttendanceCheckDate clubMemberAttendanceCheckDate = new ClubMemberAttendanceCheckDate(
        clubControl.getAttendanceWeek());

    return ClubMemberDetailResDto.builder()
        .clubName(club.getClubName())
        .myClubMemberId(clubMember.getMemberId())
        .myClubGrade(myClubGrade.getGrade())
        .clubMemberAttendanceCheckDate(clubMemberAttendanceCheckDate)
        .clubMembers(clubMemberResponses)
        .build();
  }

  /**
   * 특정 클럽의 특정 회원에 대한 클럽 회원 정보를 반환합니다.
   *
   * @return 클럽 회원 정보를 담은 ClubMemberInformationResDto 객체
   * @throws EntityNotFoundException 클럽, 회원 또는 회원 정보를 찾을 수 없는 경우
   */
  public ClubMemberInformationResDto getClubMemberInformationResDto(Long clubMemberId) {
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    Member member = memberRepository.findById(clubMember.getClubMemberId())
        .orElseThrow(EntityNotFoundException::new);
    ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        clubMember.getClubMemberInformationId()).orElseThrow(EntityNotFoundException::new);

    return ClubMemberInformationResDto.builder()
        .name(member.getFullName())
        .major(member.getMajor())
        .studentId(member.getStudentId())
        .selfIntroduction(clubMemberInformation.getIntroduce())
        .build();
  }

  /**
   * ClubMember 객체 리스트로부터 ClubMemberResponse 객체 리스트를 생성하여 반환합니다. 각 ClubMemberResponse 객체는
   * ClubMember와 관련된 Member와 AttendanceState 정보를 포함합니다.
   *
   * @param clubMembers ClubMember 객체 리스트
   * @return ClubMemberResponse 객체 리스트
   * @throws EntityNotFoundException ClubMember 또는 AttendanceState를 찾을 수 없는 경우
   */
  private List<ClubMemberResponse> getClubMemberResponsesFromClubMembers(
      List<ClubMember> clubMembers) {
    List<ClubMemberResponse> clubMemberResponses = new ArrayList<>();

    // 모든 클럽멤버들의 정보를 이용해 Response을 생성
    for (ClubMember clubMember : clubMembers) {
      Member member = memberRepository.findById(clubMember.getMemberId())
          .orElseThrow(EntityNotFoundException::new);
      AttendanceState attendanceState = attendanceStateRepository.findById(
          clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
      // 최신 정보 가져오기
      AttendanceWeekDate lastAttendanceWeekDate = getAttendanceWeekDate(
          attendanceState);
      AttendanceStates attendanceStates = new AttendanceStates(lastAttendanceWeekDate);
      // 출석 휴가 토큰 최근 정보 가져오기
      VacationToken vacationToken = getLastVacationToken(attendanceState);

      clubMemberResponses.add(
          ClubMemberResponse.builder()
              .clubMemberId(clubMember.getClubMemberId())
              .memberName(member.getFullName())
              .vacationToken(vacationToken.getVacationToken())
              .attendanceStatus(attendanceStates)
              .build()
      );
    }
    return clubMemberResponses;
  }

  /**
   * 주어진 출석 상태에서 마지막 휴가 토큰을 반환하는 메서드입니다.
   *
   * @param attendanceState 출석 상태 객체
   * @return 마지막 휴가 토큰. 만약 목록이 비어 있거나 null이면 null을 반환합니다.
   */
  private VacationToken getLastVacationToken(final AttendanceState attendanceState) {
    List<VacationToken> vacationTokens = attendanceState.getVacationTokens();
    Integer lastIndex = vacationTokens.size() - 1;
    VacationToken vacationToken = vacationTokens.get(lastIndex);

    return vacationToken;
  }

  /**
   * 주어진 회원 객체 리스트에서 각 회원의 전체 이름을 추출하여 문자열 리스트로 반환합니다.
   *
   * @param members 전체 이름을 추출할 회원 객체 리스트
   * @return 각 회원의 전체 이름으로 이루어진 문자열 리스트
   */
  private List<String> getFullNamesFromMembers(List<Member> members) {
    return members.stream()
        .map(Member::getFullName)
        .toList();
  }

  /**
   * 출석 상태에서 마지막 출석 주차 날짜를 반환합니다.
   *
   * @param attendanceState 출석 상태
   * @return 마지막 출석 주차 날짜, 만약 목록이 비어 있거나 null이면 null을 반환합니다.
   */
  private AttendanceWeekDate getAttendanceWeekDate(final AttendanceState attendanceState) {
    List<AttendanceWeekDate> attendanceWeekDates = attendanceState.getAttendanceWeekDates();
    Integer lastIndex = attendanceWeekDates.size() - 1;
    AttendanceWeekDate lastAttendanceWeekDate = attendanceWeekDates.get(lastIndex);

    return lastAttendanceWeekDate;
  }

  /**
   * 주어진 ClubMember list 부터 실제 Member 객체을 반환
   *
   * @param clubMembers ClubMember list
   * @return Member List 반환
   */
  private List<Member> getMembersFromClubMember(List<ClubMember> clubMembers) {
    return clubMembers.stream()
        .map(clubMember -> memberRepository.findById(clubMember.getMemberId())
            .orElseThrow(EntityNotFoundException::new))
        .toList();
  }
}
