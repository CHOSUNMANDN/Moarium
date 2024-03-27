package back.springbootdeveloper.seungchan.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.SearchResult;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@MoariumSpringBootTest
class SearchServiceTest {

  @Autowired
  protected ObjectMapper objectMapper;
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
  void 메인_페이지_동아리_검색_조회_테스트() throws Exception {
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/main/search";
    final Long targetClubId = targetClubOneId;
    final String search = "팀";

    // 검증을 위한 데이터 준비
    List<SearchResult> searchResults = new ArrayList<>();
    List<Club> clubs = clubRepository.findAll();

    for (final Club club : clubs) {
      String clubName = club.getClubName();
      if (clubName.contains(search) && !search.isEmpty()) {
        searchResults.add(
            SearchResult.builder()
                .clubName(clubName)
                .clubProfileImage("")
                .build()
        );
      }
    }

    // when
    ResultActions result = mockMvc.perform(get(url, targetClubId).param("search", search)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk());
    for (int i = 0; i < searchResults.size(); i++) {
      result
          .andExpect(jsonPath("$.result.searchResults[" + i + "].clubName").value(
              searchResults.get(i).getClubName()));
    }
  }
}