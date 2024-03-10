package back.springbootdeveloper.seungchan.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformation;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
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
}