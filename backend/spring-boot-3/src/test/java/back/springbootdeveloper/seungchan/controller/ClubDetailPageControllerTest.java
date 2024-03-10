package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS;
import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberReqDto;
import back.springbootdeveloper.seungchan.dto.request.GiveVacationTokenReqDto;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberAttendanceCheckDate;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import back.springbootdeveloper.seungchan.service.AttendanceNumberService;
import back.springbootdeveloper.seungchan.service.AttendanceWeekDateService;
import back.springbootdeveloper.seungchan.service.AttendanceWeekService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.VacationTokenService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import back.springbootdeveloper.seungchan.testutil.TestUtil;
import back.springbootdeveloper.seungchan.util.DayUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MoariumSpringBootTest()
class ClubDetailPageControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubMemberInformationRepository clubMemberInformationRepository;
  @Autowired
  private AttendanceStateRepository attendanceStateRepository;
  @Autowired
  private AttendanceNumberService attendanceNumberService;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private AttendanceWeekDateRepository attendanceWeekDateRepository;
  @Autowired
  private ClubGradeRepository clubGradeRepository;
  @Autowired
  private VacationTokenService vacationTokenService;
  @Autowired
  private AttendanceWeekDateService attendanceWeekDateService;
  @Autowired
  private AttendanceWeekRepository attendanceWeekRepository;
  @Autowired
  private AttendanceWeekService attendanceWeekService;
  @Autowired
  private AttendanceNumberRepository attendanceNumberRepository;
  private String token;
  private Member memberOneClubLeader;
  private Long targetClubOneId;

  @BeforeEach
  void setUp() {
    token = testCreateUtil.create_token_two_club_deputy_leader_member();
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 회원_휴면_페이지_조회() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/dormancys";

    List<ClubMember> clubMemberDormants
        = clubMemberRepository.findAllByClubIdAndClubGradeId(targetClubOneId,
        CLUB_GRADE.DORMANT.getId());

    List<Member> memberDormants = new ArrayList<>();
    clubMemberDormants.forEach(clubMember ->
        memberDormants.add(memberRepository.findById(clubMember.getMemberId()).get()));

    // when
    ResultActions result = mockMvc.perform(get(url, targetClubOneId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.result.dormancyMembers", Matchers.hasSize(clubMemberDormants.size())))
        .andExpect(
            jsonPath("$.result.dormancyMembers[0]").value(memberDormants.get(0).getFullName()))
        .andExpect(
            jsonPath("$.result.dormancyMembers[1]").value(memberDormants.get(1).getFullName()));
  }


  @Test
  void 동아리_출석_번호_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/attendance/number";

    final Club club = clubRepository.findById(targetClubOneId).get();

    // 검증을 위한 데이터 준비
    // 클럽관련 정보
    AttendanceNumber attendanceNumber = attendanceNumberService.findLastOneByClubId(
        club.getClubId());

    // when
    ResultActions result = mockMvc.perform(get(url, club.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.attendanceCheckNumber").value(
            attendanceNumber.getAttendanceNumber()));
  }

  @Test
  void 동아리_상세_페이지_조회() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details";

    final Club club = clubRepository.findById(targetClubOneId).get();
    final Member member = memberOneClubLeader;
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
        member.getMemberId()).get();

    // 검증을 위한 데이터 준비
    // 클럽관련 정보
    final String resultClubName = club.getClubName();
    final String resultMyClubGrade = clubGradeRepository.findById(clubMember.getClubGradeId()).get()
        .getClubGradeString();
    // 나의 Club Member Id 가져오기
    final Long resultMyClubMemberId = clubMember.getMemberId();
    // 클럽 관련 출석 체크 요일 여부 확인
    final AttendanceWeek attendanceWeek = attendanceWeekService.findByClubId(club.getClubId());
    final ClubMemberAttendanceCheckDate targetClubMemberAttendanceCheckDate = new ClubMemberAttendanceCheckDate(
        attendanceWeek);

    // when
    ResultActions result = mockMvc.perform(get(url, targetClubOneId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.clubName").value(resultClubName))
        .andExpect(jsonPath("$.result.myClubGrade").value(resultMyClubGrade))
        .andExpect(jsonPath("$.result.myClubMemberId").value(resultMyClubMemberId))
        .andExpect(jsonPath("$.result.clubMemberAttendanceCheckDate").value(
            targetClubMemberAttendanceCheckDate));
  }

  @Test
  void 회원_상세_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}";

    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        targetClubMember.getClubMemberInformationId()).get();

    final String targetName = targetMember.getFullName();
    final String targetMajor = targetMember.getMajor();
    final String targetStudentId = targetMember.getStudentId();
    final String targetSelfIntroduction = clubMemberInformation.getIntroduce();

    // when
    ResultActions result = mockMvc.perform(
        get(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.name").value(targetName))
        .andExpect(jsonPath("$.result.major").value(targetMajor))
        .andExpect(jsonPath("$.result.studentId").value(targetStudentId))
        .andExpect(jsonPath("$.result.selfIntroduction").value(targetSelfIntroduction));
  }

  @Test
  void 동아리_소개_페이지_휴가_제공_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/vacation";

    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceState targetAttendanceState = attendanceStateRepository.findById(
        targetClubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
    final VacationToken targetVacationToken = vacationTokenService.getLatelyVacationToken(
        targetClubMember.getClubMemberId());

    final Integer targetTokenCount = 5;
    final GiveVacationTokenReqDto requestDto = GiveVacationTokenReqDto.builder()
        .vacationToken(targetTokenCount)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final VacationToken updateVacationToken = vacationTokenService.getLatelyVacationToken(
        targetClubMember.getClubMemberId());

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_UPDATE_VACATION_TOKEN(targetMember.getFullName(),
                targetTokenCount)));

    assertThat(updateVacationToken.getVacationToken()).isEqualTo(
        targetVacationToken.getVacationToken() + targetTokenCount);
  }

  @Test
  void 동아리_소개_페이지_휴가_제공_예외_일반회원_검증_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/vacation";

    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceState targetAttendanceState = attendanceStateRepository.findById(
        targetClubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
    final Integer targetTokenCount = 5;
    final GiveVacationTokenReqDto requestDto = GiveVacationTokenReqDto.builder()
        .vacationToken(targetTokenCount)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_NOT_LEADER_CLUB.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 동아리_소개_페이지_회원_추방_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/expulsion";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    Boolean resultDelete = false;
    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubMember deleteClubMember;
    try {
      deleteClubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
          targetMember.getMemberId()).get();
    } catch (NoSuchElementException e) {
      resultDelete = true;
    }
    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_EXPULSION_MEMBER(targetMember.getFullName())));
    assertThat(resultDelete).isTrue();
  }

  @Test
  void 동아리_소개_페이지_회원_추방_예외_실장_대상_검증_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/expulsion";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_NOT_LEADER_CLUB.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

  }

  @Test
  void 동아리_소개_페이지_회원_추방_예외_대상_대표_검증_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/expulsion";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_TARGET_LEADER_MEMBER.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

  }

  @Test
  void 동아리_소개_페이지_회원_휴면_전환_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/dormancy";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubMember resultClubMember = clubMemberRepository.findById(
        targetClubMember.getClubGradeId()).get();
    final Long resultClubGradId = resultClubMember.getClubGradeId();

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_UPDATE_CLUB_GRADE(targetMember.getFullName())));

    assertThat(Integer.valueOf(String.valueOf(resultClubGradId))).isEqualTo(
        CLUB_GRADE.DORMANT.getId());
  }

  @Test
  void 동아리_소개_페이지_회원_휴면_전환_예외_휴면_멤버_여부_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/dormancy";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();

    // 타겟 멤버를 휴면 유저로 전환
    targetClubMember.updateClubGradeId(CLUB_GRADE.DORMANT.getId());
    final ClubMember badUpdateTargetClubMember = clubMemberRepository.save(targetClubMember);

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, badUpdateTargetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.ALREADY_DORMANT_MEMBER(targetMember.getFullName())))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

  }

  @Test
  void 동아리_소개_페이지_회원_휴면_전환_예외_실장_로그인_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/dormancy";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_NOT_LEADER_CLUB.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

  }

  @Test
  void 동아리_소개_페이지_회원_휴면_전환_예외_대상_대표_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/dormancy";

    // 타겟 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_TARGET_LEADER_MEMBER.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

  }

  @Test
  void 동아리_소개_페이지_출석_번호_입력_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/attendance";

    // 타겟 유저
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final AttendanceNumber targetLastAttendanceNumber = attendanceNumberService.findLastOneByClubId(
        targetClub.getClubId());
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceNumberReqDto requestDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(targetLastAttendanceNumber.getAttendanceNumber())
        .build();
    final AttendanceWeekDate targetAttendanceWeekDate = attendanceWeekDateService.getLast(
        targetClubOneId, targetMember.getMemberId());
    final ATTENDANCE_STATE targetState = targetAttendanceWeekDate.getAttendanceStateForDay(
        DayUtil.getTodayDayOfWeek());

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final AttendanceWeekDate resultAttendanceWeekDate = attendanceWeekDateService.getLast(
        targetClubOneId, targetMember.getMemberId());
    final ATTENDANCE_STATE resultTargetState = resultAttendanceWeekDate.getAttendanceStateForDay(
        DayUtil.getTodayDayOfWeek());

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(ResponseMessage.PASS_TODAY_ATTENDANCE.get()));

    assertThat(resultTargetState).isEqualTo(ATTENDANCE_STATE.ATTENDANCE);
    assertThat(resultTargetState).isNotEqualTo(targetState);
  }

  @Test
  void 동아리_소개_페이지_출석_번호_입력_예외_클럽_지정_출석_체크_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/attendance";

    // 타겟 유저
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final AttendanceNumber targetLastAttendanceNumber = attendanceNumberService.findLastOneByClubId(
        targetClub.getClubId());
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceNumberReqDto requestDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(targetLastAttendanceNumber.getAttendanceNumber())
        .build();

    final ClubControl targetClubControl = targetClub.getClubControl();
    final AttendanceWeek targetAttendanceWeek = targetClubControl.getAttendanceWeek();
    targetAttendanceWeek.updateStatusForDay(DayUtil.getTodayDayOfWeek(),
        POSSIBLE_STATUS.IMPOSSIBLE);
    attendanceWeekRepository.save(targetAttendanceWeek);

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(
            jsonPath("$.message").value(ResponseMessage.BAD_REQUEST_NOT_CLUB_CHECK_STATE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 동아리_소개_페이지_출석_번호_입력_예외_로그인_회원_타겟_회원_확인_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/attendance";

    // 타겟 유저
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final AttendanceNumber targetLastAttendanceNumber = attendanceNumberService.findLastOneByClubId(
        targetClub.getClubId());
    // 로그인 외 다른 유저
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceNumberReqDto requestDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(targetLastAttendanceNumber.getAttendanceNumber())
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(
            jsonPath("$.message").value(ResponseMessage.BAD_NOT_SAME_LOGIN_TARGET_MEMBER.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 동아리_소개_페이지_출석_번호_입력_예외_휴면_계정_확인_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/attendance";

    // 타겟 유저
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final AttendanceNumber targetLastAttendanceNumber = attendanceNumberService.findLastOneByClubId(
        targetClub.getClubId());
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceNumberReqDto requestDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(targetLastAttendanceNumber.getAttendanceNumber())
        .build();

    // 타겟 유저 휴면 계정 전환
    targetClubMember.updateClubGradeId(CLUB_GRADE.DORMANT.getId());
    final ClubMember targetDormantUpdateMemberClub = clubMemberRepository.save(targetClubMember);

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetDormantUpdateMemberClub.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(
            jsonPath("$.message").value(ResponseMessage.BAD_DORMANT_TODAY_ATTENDANCE_STATE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 동아리_소개_페이지_출석_번호_입력_예외_이미_출석_완료_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/{club_member_id}/attendance";

    // 타겟 유저
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final AttendanceNumber targetLastAttendanceNumber = attendanceNumberService.findLastOneByClubId(
        targetClub.getClubId());
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClubOneId, targetMember.getMemberId()).get();
    final AttendanceNumberReqDto requestDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(targetLastAttendanceNumber.getAttendanceNumber())
        .build();

    // 출석 상태로 전환
    AttendanceWeekDate targetAttendanceWeekDate = attendanceWeekDateService.getLast(targetClubOneId,
        targetMember.getMemberId());
    targetAttendanceWeekDate.updateAttendanceAtToday();
    targetAttendanceWeekDate = attendanceWeekDateRepository.save(targetAttendanceWeekDate);

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url, targetClubOneId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then

    result
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_ALREADY_TODAY_UPDATE_ATTENDANCE_STATE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }
}