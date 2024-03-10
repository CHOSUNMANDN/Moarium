package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.CheckDuplicationClubNameReqDto;
import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformation;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberCustomInformation;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.CustomClubApplyInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@MoariumSpringBootTest
class AdminLeaderControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubMemberInformationRepository clubMemberInformationRepository;
  @Autowired
  private MemberRepository memberRepository;
  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 지원자_확인_페이지() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member";
    final Member loginMember = testCreateUtil.get_entity_one_club_leader_member();
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();

    // 검증을 위한 데이터 준비
    List<TempMembersInformation> tempMembersInformations = new ArrayList<>();
    List<ClubMember> targetClubMemberOfTempMembers = clubMemberRepository.findAllByClubIdAndClubGradeId(
        targetClubId,
        CLUB_GRADE.TEMP_MEMBER.getId());

    for (final ClubMember targetClubMemberOfTempMember : targetClubMemberOfTempMembers) {
      Member targetMember = memberRepository.findById(targetClubMemberOfTempMember.getMemberId())
          .get();
      tempMembersInformations.add(
          new TempMembersInformation(targetClubMemberOfTempMember.getClubMemberId(), targetMember));
    }

    // when
    ResultActions result = mockMvc.perform(get(url, targetClubId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    for (int i = 0; i < targetClubMemberOfTempMembers.size(); i++) {
      result
          .andExpect(jsonPath("$.result.tempMembersInformations[" + i + "].clubMemberId").value(
              tempMembersInformations.get(i).getClubMemberId()))
          .andExpect(jsonPath("$.result.tempMembersInformations[" + i + "].name").value(
              tempMembersInformations.get(i).getName()))
          .andExpect(jsonPath("$.result.tempMembersInformations[" + i + "].email").value(
              tempMembersInformations.get(i).getEmail()))
          .andExpect(jsonPath("$.result.tempMembersInformations[" + i + "].major").value(
              tempMembersInformations.get(i).getMajor()))
          .andExpect(jsonPath("$.result.tempMembersInformations[" + i + "].studentId").value(
              tempMembersInformations.get(i).getStudentId()));
    }
  }

  @Test
  @Transactional
  void 개인_신청_신입_회원_정보_확인_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member/{club_member_id}";
    final Member loginMember = testCreateUtil.get_entity_one_club_leader_member();
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();

    // 검증을 위한 데이터 준비
    final ClubMember targetClubMember = clubMemberRepository.findAllByClubIdAndClubGradeId(
        targetClubId, CLUB_GRADE.TEMP_MEMBER.getId()).get(0);
    final Member targetMember = memberRepository.findById(targetClubMember.getMemberId()).get();
    final ClubMemberInformation targetClubMemberInformation = clubMemberInformationRepository.findById(
        targetClubMember.getClubMemberInformationId()).get();
    final List<ClubMemberCustomInformation> targetClubMemberCustomInformations = targetClubMemberInformation.getClubMemberCustomInformations();
    List<CustomInformation> targetCustomInformations = new ArrayList<>();

    for (final ClubMemberCustomInformation clubMemberCustomInformation : targetClubMemberCustomInformations) {
      CustomClubApplyInformation customClubApplyInformation = clubMemberCustomInformation.getCustomClubApplyInformation();

      targetCustomInformations.add(
          new CustomInformation(clubMemberCustomInformation, customClubApplyInformation));
    }

    // when
    ResultActions result = mockMvc.perform(
        get(url, targetClubId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(jsonPath("$.result.clubMemberId").value(
            targetClubMember.getClubMemberId()))
        .andExpect(jsonPath("$.result.name").value(
            targetMember.getFullName()))
        .andExpect(jsonPath("$.result.email").value(
            targetMember.getEmail()))
        .andExpect(jsonPath("$.result.major").value(
            targetMember.getMajor()))
        .andExpect(jsonPath("$.result.studentId").value(
            targetMember.getStudentId()));

    for (int i = 0; i < targetCustomInformations.size(); i++) {
      result
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customInformationId").value(
              targetCustomInformations.get(i).getCustomInformationId()))
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customContent").value(
              targetCustomInformations.get(i).getCustomContent()))
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customType").value(
              targetCustomInformations.get(i).getCustomType()));
    }
  }

  @Test
  void 신청_신입_회원_정회원_전환_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member/{club_member_id}/accept";
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();

    // 검증 준비
    final ClubMember targetClubMember = clubMemberRepository.findAllByClubIdAndClubGradeId(
        targetClubId, CLUB_GRADE.TEMP_MEMBER.getId()).get(0);

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );
    final ClubMember resultClubMember = clubMemberRepository.findById(
        targetClubMember.getClubMemberId()).get();

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.SUCCESS_ACCEPT_TEMP_MEMBER.get()));

    assertThat(resultClubMember.getClubGradeId()).isEqualTo(CLUB_GRADE.MEMBER.getId().longValue());
  }

  @Test
  void 신청_신입_회원_정회원_전환_예외_임시_회원이_아닌_회원_테스트_1() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member/{club_member_id}/accept";
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();
    final Member loginMember = testCreateUtil.get_entity_one_club_leader_member();

    // 검증 준비
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubId,
        loginMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_ACCEPT_TEMP_MEMBER.get()));
  }

  @Test
  void 신청_신입_회원_정회원_전환_예외_임시_회원이_아닌_회원_테스트_2() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member/{club_member_id}/accept";
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();
    final Member loginMember = testCreateUtil.get_entity_one_club_deputy_leader_member();

    // 검증 준비
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubId,
        loginMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_ACCEPT_TEMP_MEMBER.get()));
  }

  @Test
  void 신청_신입_회원_정회원_전환_예외_임시_회원이_아닌_회원_테스트_3() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member/{club_member_id}/accept";
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();
    final Member loginMember = testCreateUtil.get_entity_one_club_normal_member();

    // 검증 준비
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(targetClubId,
        loginMember.getMemberId()).get();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClubId, targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_ACCEPT_TEMP_MEMBER.get()));
  }
}