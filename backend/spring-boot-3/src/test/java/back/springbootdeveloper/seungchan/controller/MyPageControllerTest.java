package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberArticlesResDto;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberCommentsResDto;
import back.springbootdeveloper.seungchan.dto.response.MyAllClubMembersAttendance;
import back.springbootdeveloper.seungchan.dto.response.MyAllClubMembersInformationsResDto;
import back.springbootdeveloper.seungchan.dto.response.MyClubArticle;
import back.springbootdeveloper.seungchan.dto.response.MyClubArticleComment;
import back.springbootdeveloper.seungchan.dto.response.MyClubMembersAttendance;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubGradeRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.ClubArticleCommentService;
import back.springbootdeveloper.seungchan.service.ClubArticleService;
import back.springbootdeveloper.seungchan.service.MyPageService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MoariumSpringBootTest
class MyPageControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private ClubGradeRepository clubGradeRepository;
  @Autowired
  private ClubArticleCommentService clubArticleCommentService;
  @Autowired
  private ClubArticleService clubArticleService;
  @Autowired
  private MyPageService myPageService;
  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 마이페이지_동아리_탈퇴하기_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/quit";
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();
    final Club targetClub = clubRepository.findById(targetClubMember.getClubId()).get();
    Boolean resultDelete = false;

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubMember deleteClubMember;
    try {
      clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
          targetMember.getMemberId()).get();
    } catch (NoSuchElementException e) {
      resultDelete = true;
    }

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_QUIT_CLUB(targetClub.getClubName())));

    assertThat(resultDelete).isTrue();
  }

  @Test
  void 마이페이지_동아리_탈퇴하기_예외_해당_계정이_로그인_회원의_계정_여부_확인_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/quit";
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_NOT_SAME_LOGIN_TARGET_MEMBER.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 마이페이지_동아리_탈퇴하기_예외_타겟_대상_실장_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/quit";
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_TARGET_LEADER_MEMBER.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 마이페이지_동아리_활동_휴_전환_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/transform";
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();
    final ClubGrade targetClubGrade = clubGradeRepository.findById(
        targetClubMember.getClubGradeId()).get();
    targetClubMember.updateClubGradeId(CLUB_GRADE.MEMBER.getId());
    clubMemberRepository.save(targetClubMember);

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubGrade resultClubGrade = clubGradeRepository.findById(
        targetClubMember.getClubGradeId()).get();
    final Boolean resultSameTargetClubGrade = targetClubGrade.getClubGrade()
        .is(resultClubGrade.getClubGrade());

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.SUCCESS_TOGLE_DORMANT_GRADE.get()));
    assertThat(resultSameTargetClubGrade).isFalse();
  }

  @Test
  void 마이페이지_동아리_휴면_활동_전환_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/transform";
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();
    final ClubGrade targetClubGrade = clubGradeRepository.findById(
        targetClubMember.getClubGradeId()).get();
    targetClubMember.updateClubGradeId(CLUB_GRADE.DORMANT.getId());
    clubMemberRepository.save(targetClubMember);

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubGrade resultClubGrade = clubGradeRepository.findById(
        targetClubMember.getClubGradeId()).get();
    final Boolean resultSameTargetClubGrade = targetClubGrade.getClubGrade()
        .is(resultClubGrade.getClubGrade());

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.SUCCESS_TOGLE_MEMBER_GRADE.get()));
    assertThat(resultSameTargetClubGrade).isFalse();
  }

  @Test
  void 마이페이지_동아리_휴면_활동_전환_예외_해당_계정이_로그인_회원의_계정_여부_확인_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/transform";
    final Member targetMember = testCreateUtil.get_entity_one_club_deputy_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_NOT_SAME_LOGIN_TARGET_MEMBER.get()));
  }

  @Test
  void 마이페이지_동아리_휴면_활동_전환_예외_대상_실장_확인_테스트() throws Exception {
    // given
    // 유저 로그인
    token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/transform";
    final Member targetMember = testCreateUtil.get_entity_one_club_leader_member();
    final ClubMember targetClubMember = clubMemberRepository.findByMemberId(
        targetMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_TARGET_LEADER_MEMBER.get()));
  }

  @Test
  void 마이페이지_동아리_내가_쓴_클럽_게시판_댓글_보기_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/comments";

    final Member member = memberOneClubLeader;
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
        member.getMemberId()).get();

    // 검증을 위한 데이터 준비
    // 클럽관련 정보
    final ClubMemberCommentsResDto resultClubMemberCommentsResDto = clubArticleCommentService.getClubMemberCommentsResDto(
        clubMember.getClubMemberId());
    final List<MyClubArticleComment> resultMyClubArticleComments = resultClubMemberCommentsResDto.getMyClubArticleComments();
    // when
    ResultActions result = mockMvc.perform(get(url, clubMember.getClubMemberId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    for (int i = 0; i < resultMyClubArticleComments.size(); i++) {
      // then
      result
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.result.myClubArticleComments[" + i + "].clubArticleId").value(
              resultMyClubArticleComments.get(i).getClubArticleId()))
          .andExpect(jsonPath("$.result.myClubArticleComments[" + i + "].clubArticleTitle").value(
              resultMyClubArticleComments.get(i).getClubArticleTitle()))
          .andExpect(jsonPath("$.result.myClubArticleComments[" + i + "].articleComment").value(
              resultMyClubArticleComments.get(i).getArticleComment()))
          .andExpect(jsonPath("$.result.myClubArticleComments[" + i + "].articleCommentDate").value(
              resultMyClubArticleComments.get(i).getArticleCommentDate()));
    }
  }

  @Test
  void 마이페이지_동아리_내가_쓴_클럽_게시판_보기_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/articles";

    final Member member = memberOneClubLeader;
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
        member.getMemberId()).get();

    // 검증을 위한 데이터 준비
    // 클럽관련 정보
    final ClubMemberArticlesResDto clubMemberCommentsResDto = clubArticleService.getClubMemberArticlesResDto(
        clubMember.getClubMemberId());
    final List<MyClubArticle> myClubArticles = clubMemberCommentsResDto.getMyClubArticles();

    // when
    ResultActions result = mockMvc.perform(get(url, clubMember.getClubMemberId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    for (int i = 0; i < myClubArticles.size(); i++) {
      // then
      result
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.result.myClubArticles[" + i + "].clubArticleId").value(
              myClubArticles.get(i).getClubArticleId()))
          .andExpect(jsonPath("$.result.myClubArticles[" + i + "].clubArticleTitle").value(
              myClubArticles.get(i).getClubArticleTitle()))
          .andExpect(jsonPath("$.result.myClubArticles[" + i + "].clubArticleDate").value(
              myClubArticles.get(i).getClubArticleDate()))
          .andExpect(
              jsonPath("$.result.myClubArticles[" + i + "].clubArticleClassification").value(
                  myClubArticles.get(i).getClubArticleClassification()));
    }
  }

  @Test
  void 마이페이지_동아리_전체_출석_현황_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/attendances";

    final Member member = memberOneClubLeader;
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
        member.getMemberId()).get();

    // 검증을 위한 데이터 준비
    // 클럽관련 정보
    final List<MyAllClubMembersAttendance> targetMyAllClubMembersAttendances = myPageService.getMyAllClubMembersAttendance(
        member.getMemberId(), clubMember.getClubMemberId());

    // when
    ResultActions result = mockMvc.perform(get(url, clubMember.getClubMemberId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    for (int i = 0; i < targetMyAllClubMembersAttendances.size(); i++) {
      // then
      result
          .andExpect(status().isOk())
          .andExpect(
              jsonPath("$.result.myAllClubMembersAttendances[" + i + "].clubName").value(
                  targetMyAllClubMembersAttendances.get(i).getClubName()))
          .andExpect(
              jsonPath("$.result.myAllClubMembersAttendances[" + i + "].vacationToken").value(
                  targetMyAllClubMembersAttendances.get(i).getVacationToken()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i + "].myAttendanceState.monday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getMonday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceState.tuesday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getTuesday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceState.wednesday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getWednesday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceState.thursday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getThursday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i + "].myAttendanceState.friday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getFriday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceState.saturday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getSaturday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i + "].myAttendanceState.sunday").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceState().getSunday()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceCount.vacation").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceCount().getVacation()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceCount.attendance").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceCount().getAttendance()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceCount.absence").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceCount().getAbsence()))
          .andExpect(
              jsonPath(
                  "$.result.myAllClubMembersAttendances[" + i
                      + "].myAttendanceCount.totalCount").value(
                  targetMyAllClubMembersAttendances.get(i).getMyAttendanceCount().getTotalCount()));
    }
  }

  @Test
  void 마이페이지_동아리_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}";

    final Member member = memberOneClubLeader;
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
        member.getMemberId()).get();
    final Club club = clubRepository.findById(clubMember.getClubId()).get();

    // 검증을 위한 데이터 준비
    // 클럽관련 정보
    String memberProfile = "향후 디비수정";
    String memberName = member.getFullName();
    String memberStudentId = member.getStudentId();
    String clubName = club.getClubName();
    String memberStatusMessage = "향후 디비수정";

    // when
    ResultActions result = mockMvc.perform(get(url, clubMember.getClubMemberId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.memberProfile").value(memberProfile))
        .andExpect(jsonPath("$.result.memberName").value(memberName))
        .andExpect(jsonPath("$.result.memberStudentId").value(memberStudentId))
        .andExpect(jsonPath("$.result.clubName").value(clubName))
        .andExpect(jsonPath("$.result.memberStatusMessage").value(memberStatusMessage));
  }

  @Test
  void 마이페이지_자정_동아리_출석_현황() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/personal-info/{club_member_id}/clubs/attendances";

    final Member member = memberOneClubLeader;
    final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubOneId,
        member.getMemberId()).get();
    final Club club = clubRepository.findById(clubMember.getClubId()).get();

    // 검증을 위한 데이터 준비
    List<MyClubMembersAttendance> myClubMembersAttendances = myPageService.getMyClubMembersAttendance(
        clubMember.getClubMemberId());

    // when
    ResultActions result = mockMvc.perform(get(url, clubMember.getClubMemberId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.clubName").value(club.getClubName()));

    for (int i = 0; i < myClubMembersAttendances.size(); i++) {
      // 출석 정보 테스트
      result.andExpect(
              jsonPath("$.result.myClubMembersAttendances[" + i + "].attendanceStates.monday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getMonday())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].attendanceStates.tuesday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getTuesday())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].attendanceStates.wednesday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getWednesday())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].attendanceStates.thursday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getThursday())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].attendanceStates.friday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getFriday())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].attendanceStates.saturday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getSaturday())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].attendanceStates.sunday").value(
                  myClubMembersAttendances.get(i).getAttendanceStates().getSunday())).

          // 출석 정보 카운터 테스트
              andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i
                      + "].myAttendanceCount.attendance").value(
                  myClubMembersAttendances.get(i).getMyAttendanceCount().getAttendance())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].myAttendanceCount.vacation").value(
                  myClubMembersAttendances.get(i).getMyAttendanceCount().getVacation())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i + "].myAttendanceCount.absence").value(
                  myClubMembersAttendances.get(i).getMyAttendanceCount().getAbsence())).
          andExpect(
              jsonPath(
                  "$.result.myClubMembersAttendances[" + i
                      + "].myAttendanceCount.totalCount").value(
                  myClubMembersAttendances.get(i).getMyAttendanceCount().getTotalCount()));
    }
  }
}