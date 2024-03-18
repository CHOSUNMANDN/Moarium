package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@MoariumSpringBootTest
class ClubIntroductionInformationControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private MemberRepository memberRepository;
  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;
  @Value("${dev-base-url}")
  private String imageBaseDirUrl;
  @Value("${club-base-url}")
  private String clubImageBaseDirUrl;
  @Value("${club-profile}")
  private String clubProfileDirUrl;
  @Value("${club-information}")
  private String clubInformationDirUrl;
  @Value("${base-image-url}")
  private String baseImageDirUrl;
  @Value("${base-image-name}")
  private String baseImageName;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }


  @Test
  @Transactional
  void 동아리_지원서_작성_API_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}";
    final Long targetClubId = targetClubOneId;
    final Club targetClub = clubRepository.findById(targetClubId).get();
    final ClubMember targetLeaderClubMember = clubMemberRepository.findLeaderByClubIdAndLeaderId(
        targetClubId,
        CLUB_GRADE.LEADER.getId());
    final Member targetLeaderMember = memberRepository.findById(
        targetLeaderClubMember.getMemberId()).get();

    // 검증을 위한 데이터 준비
    final String resultClubName = targetClub.getClubName();
    final String resultClubIntroduction = targetClub.getClubIntroduce();
    final String resultLeaderMemberName = targetLeaderMember.getFullName();

    // when
    ResultActions result = mockMvc.perform(get(url, targetClubId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    System.out.println("this.baseImageDirUrl = " + this.baseImageDirUrl);

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.clubName").value(resultClubName))
        .andExpect(jsonPath("$.result.clubIntroduction").value(resultClubIntroduction))
        .andExpect(jsonPath("$.result.clubLeaderName").value(resultLeaderMemberName));
  }
}