package back.springbootdeveloper.seungchan.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.NotLeaderException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@MoariumSpringBootTest
class MyValidationTest {

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
  @Autowired
  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 팀의_리더_검증_테스트() throws Exception {
    // given
    // 리더가 아닌 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/informations/{club_id}/details/leader/temp/member";
    final Long targetClubId = testCreateUtil.getONE_CLUB_ID();

    // when
    HttpServletRequest request = mockMvc.perform(get(url, targetClubId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    ).andReturn().getRequest();
    
    // then
    assertThatThrownBy(() -> MyValidation.isLeaderMember(tokenService, request, targetClubId))
        .isInstanceOf(NotLeaderException.class);
  }
}