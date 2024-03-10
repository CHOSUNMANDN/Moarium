package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.ApplyMemberToClubReqDto;
import back.springbootdeveloper.seungchan.dto.request.CustomInformationReqForm;
import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberCustomInformation;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.ClubMemberCustomService;
import back.springbootdeveloper.seungchan.service.CustomClubApplyInformationService;
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
class ClubApplyControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private CustomClubApplyInformationService customClubApplyInformationService;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubMemberInformationRepository clubMemberInformationRepository;
  @Autowired
  private ClubMemberCustomService clubMemberCustomService;

  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 동아리_지원서_작성_API_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/{club_id}/apply";
    //
    final Club targetClub = clubRepository.findById(testCreateUtil.getTWO_CLUB_ID()).get();
    final Member loginMember = testCreateUtil.get_entity_one_club_deputy_leader_member();

    // 검증을 위한 데이터 준비
    String selfIntroduction = "테스트 자기 소개";
    String customContent = "테스트 커스텀 대답";
    List<CustomInformation> customClubApplyInformations = customClubApplyInformationService.findAllCustomInformationByClubId(
        targetClub.getClubId());
    List<CustomInformationReqForm> customInformations = new ArrayList<>();

    for (final CustomInformation customClubApplyInformation : customClubApplyInformations) {
      customInformations.add(
          CustomInformationReqForm.builder()
              .customInformationId(customClubApplyInformation.getCustomInformationId())
              .customContent(customContent)
              .build()
      );
    }
    ApplyMemberToClubReqDto requestDto = ApplyMemberToClubReqDto.builder()
        .selfIntroduction(selfIntroduction)
        .customInformations(customInformations)
        .build();
    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(post(url, targetClub.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    ClubMember resultClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), loginMember.getMemberId()).get();
    ClubMemberInformation resultClubMemberInformation = clubMemberInformationRepository.findById(
        resultClubMember.getClubMemberInformationId()).get();
    List<String> clubMemberCustomContents = clubMemberCustomService.findAllCustomContentByClubMemberInformation(
        resultClubMemberInformation.getClubMemberInformationId());

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.SUCCESS_APPLY_CLUB.get()));

    assertThat(resultClubMember.getClubGradeId()).isEqualTo(
        Long.valueOf(CLUB_GRADE.TEMP_MEMBER.getId()));
    assertThat(resultClubMemberInformation.getIntroduce()).isEqualTo(selfIntroduction);

    for (int i = 0; i < clubMemberCustomContents.size(); i++) {
      assertThat(clubMemberCustomContents.get(i)).isEqualTo(
          customInformations.get(i).getCustomContent());
    }
  }

  @Test
  void 동아리_지원서_작성_API_예외_이미_클럽에_가입_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/{club_id}/apply";
    //
    final Club targetClub = clubRepository.findById(testCreateUtil.getONE_CLUB_ID()).get();
    final Member loginMember = testCreateUtil.get_entity_one_club_leader_member();

    // 검증을 위한 데이터 준비
    String selfIntroduction = "테스트 자기 소개";
    String customContent = "테스트 커스텀 대답";
    List<CustomInformation> customClubApplyInformations = customClubApplyInformationService.findAllCustomInformationByClubId(
        targetClub.getClubId());
    List<CustomInformationReqForm> customInformations = new ArrayList<>();

    for (final CustomInformation customClubApplyInformation : customClubApplyInformations) {
      customInformations.add(
          CustomInformationReqForm.builder()
              .customInformationId(customClubApplyInformation.getCustomInformationId())
              .customContent(customContent)
              .build()
      );
    }
    ApplyMemberToClubReqDto requestDto = ApplyMemberToClubReqDto.builder()
        .selfIntroduction(selfIntroduction)
        .customInformations(customInformations)
        .build();
    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(post(url, targetClub.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_ALREADY_REGISTRATION_CLUB.get()));
  }

  @Test
  void 동아리_지원서_작성_API_예외_2번_신청_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/{club_id}/apply";
    //
    final Club targetClub = clubRepository.findById(testCreateUtil.getTWO_CLUB_ID()).get();
    final Member loginMember = testCreateUtil.get_entity_one_club_deputy_leader_member();

    // 검증을 위한 데이터 준비
    String selfIntroduction = "테스트 자기 소개";
    String customContent = "테스트 커스텀 대답";
    List<CustomInformation> customClubApplyInformations = customClubApplyInformationService.findAllCustomInformationByClubId(
        targetClub.getClubId());
    List<CustomInformationReqForm> customInformations = new ArrayList<>();

    for (final CustomInformation customClubApplyInformation : customClubApplyInformations) {
      customInformations.add(
          CustomInformationReqForm.builder()
              .customInformationId(customClubApplyInformation.getCustomInformationId())
              .customContent(customContent)
              .build()
      );
    }
    ApplyMemberToClubReqDto requestDto = ApplyMemberToClubReqDto.builder()
        .selfIntroduction(selfIntroduction)
        .customInformations(customInformations)
        .build();
    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    // 신청 1번
    mockMvc.perform(post(url, targetClub.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // 신청 2번
    ResultActions result = mockMvc.perform(post(url, targetClub.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_ALREADY_APPLY_CLUB.get()));
  }

  @Test
  void 동아리_지원서_작성_API_커스텀_조회() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/{club_id}/apply/find";
    final Club targetClub = clubRepository.findById(targetClubOneId).get();

    // 검증을 위한 데이터 준비
    final String targetClubName = targetClub.getClubName();
    List<CustomInformation> customInformations = customClubApplyInformationService
        .findAllCustomInformationByClubId(targetClub.getClubId());

    // when
    ResultActions result = mockMvc.perform(get(url, targetClub.getClubId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.clubName").value(
            targetClub.getClubName()));
    for (int i = 0; i < customInformations.size(); i++) {

      result
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customInformationId").value(
              customInformations.get(i).getCustomInformationId()))
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customContent").value(
              customInformations.get(i).getCustomContent()))
          .andExpect(jsonPath("$.result.customInformations[" + i + "].customType").value(
              customInformations.get(i).getCustomType()));
    }
  }
}