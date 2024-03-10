package back.springbootdeveloper.seungchan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.CheckDuplicationClubNameReqDto;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@MoariumSpringBootTest
class ClubRegistrationControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private ClubRegistrationController controller;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubRepository clubRepository;
  private Member memberOneClubLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 팀이름_중복_검사_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/main/club/apply/overlap";
    final Member targetMember = memberOneClubLeader;

    // 검증 준비
    final String targetClubName = "테스트를 위한 클럽 이름";
    final CheckDuplicationClubNameReqDto requestDto = CheckDuplicationClubNameReqDto.builder()
        .clubName(targetClubName)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE_VALUE.SUCCESS_NOT_DUPLICATION_CLUBNAME(targetClubName)));
  }

  @Test
  void 팀이름_중복_검사_예외_중복_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/main/club/apply/overlap";
    final Member targetMember = memberOneClubLeader;

    // 검증 준비
    final String targetClubName = clubRepository.findAll().get(0).getClubName();
    final CheckDuplicationClubNameReqDto requestDto = CheckDuplicationClubNameReqDto.builder()
        .clubName(targetClubName)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_DUPLICATION_CLUBNAME.get()));
  }
}