package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.dto.request.LoginFirstReqDto;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@MoariumSpringBootTest
class OAuthLoginControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
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
  void 초기_로그인_API() throws Exception {
    // 유저 로그인
    final String url = "/users/login/google/first";

    // 검증 준비
    final String testName = "테스트";
    final String testNickName = "테스트 닉네임";
    final String testEmail = "test1234@gmail.com";
    final String testMajor = "컴퓨터 공학과";
    final String testStudentId = "12345678";

    LoginFirstReqDto requestDto = LoginFirstReqDto.builder()
        .name(testName)
        .nickName(testNickName)
        .email(testEmail)
        .major(testMajor)
        .studentId(testStudentId)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);
    ResultActions result = mockMvc.perform(
        post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
    );

    Member targetMember = memberRepository.findByEmail(testEmail).get();
    assertThat(targetMember.getFullName()).isEqualTo(testName);
    assertThat(targetMember.getNickName()).isEqualTo(testNickName);
    assertThat(targetMember.getEmail()).isEqualTo(testEmail);
    assertThat(targetMember.getMajor()).isEqualTo(testMajor);
    assertThat(targetMember.getStudentId()).isEqualTo(testStudentId);
  }
}