package back.springbootdeveloper.seungchan.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.constant.entity.FAVORITE_CHECK;
import back.springbootdeveloper.seungchan.dto.response.ClubFindInformation;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.service.ImageService;
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
class ClubListControllerTest {

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
  private MemberRepository memberRepository;
  @Autowired
  private ImageService imageService;
  @Autowired
  private ClubMemberInformationRepository clubMemberInformationRepository;
  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  @Transactional
  void 클럽_리스트의_간략한_동아리_정보_목록_조회() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations";
    final Long targetClubId = targetClubOneId;
    final Member loginMember = testCreateUtil.get_entity_one_club_leader_member();
    final Club targetClub = clubRepository.findById(targetClubId).get();
    final ClubMember targetLeaderClubMember = clubMemberRepository.findLeaderByClubIdAndLeaderId(
        targetClubId,
        CLUB_GRADE.LEADER.getId());

    // 검증을 위한 데이터 준비
    List<ClubMember> joinClubMembers = clubMemberRepository.findAllByMemberId(
        loginMember.getMemberId());
    List<ClubFindInformation> clubFindInformations = new ArrayList<>();

    // loginMember의 참여한 클럽의 리스트
    for (final ClubMember joinClubMember : joinClubMembers) {
      // 클럽의 즐겨찾기 여부 가져오기
      ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
          joinClubMember.getClubMemberInformationId()).orElseThrow(EntityNotFoundException::new);
      FAVORITE_CHECK favoriteCheck = clubMemberInformation.getFavoriteCheck();
      Club club = clubRepository.findById(joinClubMember.getClubId())
          .orElseThrow(EntityNotFoundException::new);
      ClubMember leaderClubMember = clubMemberRepository.findLeaderByClubIdAndLeaderId(
          club.getClubId(),
          CLUB_GRADE.LEADER.getId()); // 리더의 정보를 조회합니다.
      Member leaderMember = memberRepository.findById(leaderClubMember.getMemberId())
          .orElseThrow(EntityNotFoundException::new);
      // 클럽의 프로필 이미지를 Base64 형식으로 가져옵니다.
      String clubProfileImage = imageService.getClubProfileImagesAsBase64(club.getClubName());
      List<ClubMember> clubMemberIncludeClubs = clubMemberRepository.findAllByClubId(
          club.getClubId());

      // 반환하는 리스트에 클럽의 관련 정보 add
      clubFindInformations.add(
          ClubFindInformation.builder()
              .clubId(club.getClubId())
              .clubProfileImage(clubProfileImage)
              .clubName(club.getClubName())
              .clubLeaderName(leaderMember.getFullName())
              .numberMember(String.valueOf(clubMemberIncludeClubs.size()))
              .favoriteCheck(favoriteCheck.getState())
              .build()
      );
    }

    // when
    ResultActions result = mockMvc.perform(get(url, targetClubId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk());

    for (int i = 0; i < clubFindInformations.size(); i++) {
      result
          .andExpect(jsonPath("$.result.clubFindInformations[" + i + "].clubId").value(
              clubFindInformations.get(i).getClubId()))
          .andExpect(jsonPath("$.result.clubFindInformations[" + i + "].clubProfileImage").value(
              clubFindInformations.get(i).getClubProfileImage()))
          .andExpect(jsonPath("$.result.clubFindInformations[" + i + "].clubName").value(
              clubFindInformations.get(i).getClubName()))
          .andExpect(
              jsonPath("$.result.clubFindInformations[" + i + "].clubLeaderName").value(
                  clubFindInformations.get(i).getClubLeaderName()))
          .andExpect(
              jsonPath("$.result.clubFindInformations[" + i + "].numberMember").value(
                  clubFindInformations.get(i).getNumberMember()));
    }
  }
}