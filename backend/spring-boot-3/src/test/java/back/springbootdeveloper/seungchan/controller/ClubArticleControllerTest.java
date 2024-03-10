package back.springbootdeveloper.seungchan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import back.springbootdeveloper.seungchan.annotation.MoariumSpringBootTest;
import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.ANONYMITY;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.dto.request.SaveClubArticleConfidential;
import back.springbootdeveloper.seungchan.dto.request.SaveClubArticleFreeAndSuggestion;
import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.dto.request.WriteSuggestionAnswerReqDto;
import back.springbootdeveloper.seungchan.dto.response.ClubArticleAnswerResDto;
import back.springbootdeveloper.seungchan.dto.response.ClubArticleCommentInformation;
import back.springbootdeveloper.seungchan.dto.response.ClubArticleDetailResDto;
import back.springbootdeveloper.seungchan.dto.response.ClubArticleSimpleInformation;
import back.springbootdeveloper.seungchan.dto.response.ClubArticleSimpleInformationResDto;
import back.springbootdeveloper.seungchan.dto.response.WriteClubArticleCommentReqDto;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.ClubArticleCommentRepository;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.ClubArticleCommentService;
import back.springbootdeveloper.seungchan.service.ClubArticleService;
import back.springbootdeveloper.seungchan.testutil.TestCreateUtil;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
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

@MoariumSpringBootTest
class ClubArticleControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestCreateUtil testCreateUtil;
  @Autowired
  private ClubRepository clubRepository;
  @Autowired
  private ClubArticleRepository clubArticleRepository;
  @Autowired
  private ClubMemberRepository clubMemberRepository;
  @Autowired
  private ClubArticleService clubArticleService;
  @Autowired
  private ClubArticleCommentService clubArticleCommentService;
  @Autowired
  private ClubArticleCommentRepository clubArticleCommentRepository;
  private Member memberOneClubLeader;
  private Member memberOneClubDeputyLeader;
  private Long targetClubOneId;
  private String token;

  @BeforeEach
  void setUp() {
    memberOneClubLeader = testCreateUtil.get_entity_one_club_leader_member();
    memberOneClubDeputyLeader = testCreateUtil.get_entity_one_club_deputy_leader_member();
    targetClubOneId = testCreateUtil.getONE_CLUB_ID();
  }

  @Test
  void 팀_게시판_수정_API_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final String updateTitle = "테스트를 위한 업데이트 클럽게시물 제목";
    final String updateContent = "테스트를 위한 업데이트 클럽게시물 내용";
    final UpdateClubArticlePutDto requestDto = UpdateClubArticlePutDto.builder()
        .clubArticleUpdateTitle(updateTitle)
        .clubArticleUpdateContent(updateContent)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleRepository.findById(
        targetClubArticle.getClubArticleId()).get();

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.UPDATE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultClubArticle.getTitle()).isEqualTo(updateTitle);
    assertThat(resultClubArticle.getContent()).isEqualTo(updateContent);
  }

  @Test
  void 팀_게시판_수정_API_예외_게시판_저자_검사_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubDeputyLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final String updateTitle = "테스트를 위한 업데이트 클럽게시물 제목";
    final String updateContent = "테스트를 위한 업데이트 클럽게시물 내용";
    final UpdateClubArticlePutDto requestDto = UpdateClubArticlePutDto.builder()
        .clubArticleUpdateTitle(updateTitle)
        .clubArticleUpdateContent(updateContent)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_UPDATE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 팀_게시판_삭제_API_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());
    Boolean resultDelete = false;

    // when
    ResultActions result = mockMvc.perform(
        delete(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle deleteClubArticle;
    try {
      deleteClubArticle = clubArticleRepository.findById(targetClubArticle.getClubArticleId())
          .get();
    } catch (NoSuchElementException e) {
      resultDelete = true;
    }

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_DELETE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultDelete).isTrue();
  }

  @Test
  void 팀_게시판_삭제_API_예외_저자_검증_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubDeputyLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // when
    ResultActions result = mockMvc.perform(
        delete(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // than
    result
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_DELETE_CLUB_ARTICLE.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 팀_게시판_상세_페이지_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final ClubArticleDetailResDto targetClubArticleDetailResDto = clubArticleService.getClubArticleDetailResDto(
        targetClub.getClubId(), targetClubArticle.getClubArticleId(), targetMember.getMemberId());
    final List<ClubArticleCommentInformation> clubArticleCommentInformations = targetClubArticleDetailResDto.getClubArticleCommentInformations();

    // when
    ResultActions result = mockMvc.perform(
        get(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(jsonPath("$.result.isClubArticleAuthor").value(
            targetClubArticleDetailResDto.getIsClubArticleAuthor()))
        .andExpect(jsonPath("$.result.clubArticleTitle").value(
            targetClubArticleDetailResDto.getClubArticleTitle()))
        .andExpect(jsonPath("$.result.clubArticleContent").value(
            targetClubArticleDetailResDto.getClubArticleContent()))
        .andExpect(jsonPath("$.result.clubArticleLikeNumber").value(
            targetClubArticleDetailResDto.getClubArticleLikeNumber()))
        .andExpect(jsonPath("$.result.clubArticleCommentNumber").value(
            targetClubArticleDetailResDto.getClubArticleCommentNumber()))
        .andExpect(jsonPath("$.result.clubArticleDate").value(
            targetClubArticleDetailResDto.getClubArticleDate()))
        .andExpect(jsonPath("$.result.clubArticleAnswerSuggestion").value(
            targetClubArticleDetailResDto.getClubArticleAnswerSuggestion()))
        .andExpect(jsonPath("$.result.clubArticleAnswerCheck").value(
            targetClubArticleDetailResDto.getClubArticleAnswerCheck()))
        .andExpect(jsonPath("$.result.clubArticleClassification").value(
            targetClubArticleDetailResDto.getClubArticleClassification()));

    for (int i = 0; i < clubArticleCommentInformations.size(); i++) {
      result
          .andExpect(jsonPath(
              "$.result.clubArticleCommentInformations[" + i + "].clubArticleCommentId").value(
              clubArticleCommentInformations.get(i).getClubArticleCommentId()))
          .andExpect(jsonPath(
              "$.result.clubArticleCommentInformations[" + i
                  + "].isClubArticleCommentAuthor").value(
              clubArticleCommentInformations.get(i).getIsClubArticleCommentAuthor()))
          .andExpect(jsonPath(
              "$.result.clubArticleCommentInformations[" + i + "].clubArticleCommentContent").value(
              clubArticleCommentInformations.get(i).getClubArticleCommentContent()))
          .andExpect(jsonPath(
              "$.result.clubArticleCommentInformations[" + i
                  + "].clubArticleCommentAuthorName").value(
              clubArticleCommentInformations.get(i).getClubArticleCommentAuthorName()))
          .andExpect(jsonPath(
              "$.result.clubArticleCommentInformations[" + i + "].clubArticleCommentDate").value(
              clubArticleCommentInformations.get(i).getClubArticleCommentDate()))
          .andExpect(jsonPath(
              "$.result.clubArticleCommentInformations[" + i + "].clubArticleCommentLike").value(
              clubArticleCommentInformations.get(i).getClubArticleCommentLike()));
    }
  }

  @Test
  void 팀_게시판_상세_페이지_좋아요() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/like";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());
    final Integer targetClubArticleCommentLikeCount = targetClubArticle.getLikeCount();

    // when
    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());
    final Integer resultClubArticleCommentLikeCount = resultClubArticle.getLikeCount();

    // than
    result
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultClubArticleCommentLikeCount).isEqualTo(targetClubArticleCommentLikeCount + 1);
  }

  @Test
  void 팀_건의_게시판_전체_페이지_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/suggestions";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final ClubArticleSimpleInformationResDto targetClubMemberSimpleInformationResDto = clubArticleService.getClubMemberSimpleInformationResDto(
        targetClub.getClubId(), targetMember.getMemberId(),
        CLUB_ARTICLE_CLASSIFICATION.SUGGESTION);
    final List<ClubArticleSimpleInformation> targetClubArticleSimpleInformations = targetClubMemberSimpleInformationResDto.getClubArticleSimpleInformations();
    // when

    ResultActions result = mockMvc.perform(
        get(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    for (int i = 0; i < targetClubArticleSimpleInformations.size(); i++) {
      result
          .andExpect(
              jsonPath("$.result.clubArticleSimpleInformations[" + i + "].clubArticleTitle").value(
                  targetClubArticleSimpleInformations.get(i).getClubArticleTitle()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleAuthorName").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleAuthorName()))
          .andExpect(
              jsonPath("$.result.clubArticleSimpleInformations[" + i + "].clubArticleDate").value(
                  targetClubArticleSimpleInformations.get(i).getClubArticleDate()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleCommentCount").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleCommentCount()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleAnswerCheck").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleAnswerCheck()));
    }
  }

  @Test
  void 팀_비밀_게시판_전체_페이지_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/confidentials";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final ClubArticleSimpleInformationResDto targetClubMemberSimpleInformationResDto = clubArticleService.getClubMemberSimpleInformationResDto(
        targetClub.getClubId(), targetMember.getMemberId(),
        CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL);
    final List<ClubArticleSimpleInformation> targetClubArticleSimpleInformations = targetClubMemberSimpleInformationResDto.getClubArticleSimpleInformations();
    // when

    ResultActions result = mockMvc.perform(
        get(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    for (int i = 0; i < targetClubArticleSimpleInformations.size(); i++) {
      result
          .andExpect(
              jsonPath("$.result.clubArticleSimpleInformations[" + i + "].clubArticleTitle").value(
                  targetClubArticleSimpleInformations.get(i).getClubArticleTitle()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleAuthorName").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleAuthorName()))
          .andExpect(
              jsonPath("$.result.clubArticleSimpleInformations[" + i + "].clubArticleDate").value(
                  targetClubArticleSimpleInformations.get(i).getClubArticleDate()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleCommentCount").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleCommentCount()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleAnswerCheck").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleAnswerCheck()));
    }
  }

  @Test
  void 팀_자유_게시판_전체_페이지_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/frees";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final ClubArticleSimpleInformationResDto targetClubMemberSimpleInformationResDto = clubArticleService.getClubMemberSimpleInformationResDto(
        targetClub.getClubId(), targetMember.getMemberId(),
        CLUB_ARTICLE_CLASSIFICATION.FREEDOM);
    final List<ClubArticleSimpleInformation> targetClubArticleSimpleInformations = targetClubMemberSimpleInformationResDto.getClubArticleSimpleInformations();
    // when

    ResultActions result = mockMvc.perform(
        get(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    for (int i = 0; i < targetClubArticleSimpleInformations.size(); i++) {
      result
          .andExpect(
              jsonPath("$.result.clubArticleSimpleInformations[" + i + "].clubArticleTitle").value(
                  targetClubArticleSimpleInformations.get(i).getClubArticleTitle()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleAuthorName").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleAuthorName()))
          .andExpect(
              jsonPath("$.result.clubArticleSimpleInformations[" + i + "].clubArticleDate").value(
                  targetClubArticleSimpleInformations.get(i).getClubArticleDate()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleCommentCount").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleCommentCount()))
          .andExpect(jsonPath(
              "$.result.clubArticleSimpleInformations[" + i + "].clubArticleAnswerCheck").value(
              targetClubArticleSimpleInformations.get(i).getClubArticleAnswerCheck()));
    }
  }

  @Test
  void 팀_건의_게시판_답변_조회_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    final ClubArticleAnswerResDto clubArticleAnswerResDto =
        clubArticleService.getClubArticleAnswerResDto(targetClub.getClubId(),
            targetMember.getMemberId(), targetClubArticle.getClubArticleId());

    // when

    ResultActions result = mockMvc.perform(
        get(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(
            jsonPath("$.result.clubArticleTitle").value(
                clubArticleAnswerResDto.getClubArticleTitle()))
        .andExpect(
            jsonPath("$.result.clubArticleContent").value(
                clubArticleAnswerResDto.getClubArticleContent()))
        .andExpect(
            jsonPath("$.result.clubArticleLikeNumber").value(
                clubArticleAnswerResDto.getClubArticleLikeNumber()))
        .andExpect(
            jsonPath("$.result.clubArticleCommentNumber").value(
                clubArticleAnswerResDto.getClubArticleCommentNumber()))
        .andExpect(
            jsonPath("$.result.clubArticleDate").value(
                clubArticleAnswerResDto.getClubArticleDate()));

  }

  @Test
  void 팀_건의_게시판_답변_쓰기_테스트_리더() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String saveClubSuggestionArticleAnswer = "건의 게시판 답변 생성 데이터";
    final WriteSuggestionAnswerReqDto requestDto = WriteSuggestionAnswerReqDto.builder()
        .clubSuggestionArticleAnswer(saveClubSuggestionArticleAnswer)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleRepository.findById(
        targetClubArticle.getClubArticleId()).get();

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_SUGGESTION_ANSWER.get()));

    assertThat(resultClubArticle.getSuggestionAnswer()).isEqualTo(saveClubSuggestionArticleAnswer);
  }

  @Test
  void 팀_건의_게시판_답변_쓰기_테스트_부_리더() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String saveClubSuggestionArticleAnswer = "건의 게시판 답변 생성 데이터";
    final WriteSuggestionAnswerReqDto requestDto = WriteSuggestionAnswerReqDto.builder()
        .clubSuggestionArticleAnswer(saveClubSuggestionArticleAnswer)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleRepository.findById(
        targetClubArticle.getClubArticleId()).get();

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_SUGGESTION_ANSWER.get()));

    assertThat(resultClubArticle.getSuggestionAnswer()).isEqualTo(saveClubSuggestionArticleAnswer);
  }

  @Test
  void 팀_건의_게시판_답변_쓰기_예외_일반_멤버_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_normal_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = testCreateUtil.get_entity_one_club_normal_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String saveClubSuggestionArticleAnswer = "건의 게시판 답변 생성 데이터";
    final WriteSuggestionAnswerReqDto requestDto = WriteSuggestionAnswerReqDto.builder()
        .clubSuggestionArticleAnswer(saveClubSuggestionArticleAnswer)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_SUGGESTION_ANSWER.get()));
  }

  @Test
  void 팀_건의_게시판_답변_수정_테스트_리더() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String updateClubSuggestionArticleAnswer = "건의 게시판 답변 수정 데이터";
    final WriteSuggestionAnswerReqDto requestDto = WriteSuggestionAnswerReqDto.builder()
        .clubSuggestionArticleAnswer(updateClubSuggestionArticleAnswer)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleRepository.findById(
        targetClubArticle.getClubArticleId()).get();

    // then
    result
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.message").value(ResponseMessage.SUCCESS_UPDATE_SUGGESTION_ANSWER.get()));

    assertThat(resultClubArticle.getSuggestionAnswer()).isEqualTo(
        updateClubSuggestionArticleAnswer);
  }

  @Test
  void 팀_건의_게시판_답변_수정_테스트_부_리더() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_deputy_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubDeputyLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String saveClubSuggestionArticleAnswer = "건의 게시판 답변 생성 데이터";
    final WriteSuggestionAnswerReqDto requestDto = WriteSuggestionAnswerReqDto.builder()
        .clubSuggestionArticleAnswer(saveClubSuggestionArticleAnswer)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleRepository.findById(
        targetClubArticle.getClubArticleId()).get();

    // then
    result
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.message").value(ResponseMessage.SUCCESS_UPDATE_SUGGESTION_ANSWER.get()));

    assertThat(resultClubArticle.getSuggestionAnswer()).isEqualTo(saveClubSuggestionArticleAnswer);
  }

  @Test
  void 팀_건의_게시판_답변_수정_예외_일반_회원_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_normal_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/answer/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = testCreateUtil.get_entity_one_club_normal_member();
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String saveClubSuggestionArticleAnswer = "건의 게시판 답변 생성 데이터";
    final WriteSuggestionAnswerReqDto requestDto = WriteSuggestionAnswerReqDto.builder()
        .clubSuggestionArticleAnswer(saveClubSuggestionArticleAnswer)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        put(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.message").value(ResponseMessage.BAD_UPDATE_SUGGESTION_ANSWER.get()))
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 팀_게시판_상세_페이지_댓글_쓰기_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/comment/write";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // 생성을 위한 데이터
    final String saveClubArticleCommentContent = "클럽 게시판 댓글 쓰기";
    final String saveClubArticleCommentAnoymity = ANONYMITY.REAL_NAME.getState();
    final WriteClubArticleCommentReqDto requestDto = WriteClubArticleCommentReqDto.builder()
        .clubArticleCommentContent(saveClubArticleCommentContent)
        .anonymity(saveClubArticleCommentAnoymity)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubArticle.getClubArticleId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticleComment resultClubArticleComment = clubArticleCommentService.getLastClubArticleComment(
        targetClubArticle.getClubArticleId());

    // then
    assertThat(resultClubArticleComment.getContent()).isEqualTo(
        saveClubArticleCommentContent);
    assertThat(resultClubArticleComment.getAnonymity().getState()).isEqualTo(
        saveClubArticleCommentAnoymity);
  }

  @Test
  void 팀_게시판_상세_페이지_댓글_삭제_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/comment/{comment_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());
    final ClubArticleComment targetClubArticleComment = clubArticleCommentService.getLastClubArticleComment(
        targetClubArticle.getClubArticleId());
    Boolean resultDelete = false;

    // when
    ResultActions result = mockMvc.perform(
        delete(url, targetClub.getClubId(), targetClubArticle.getClubArticleId(),
            targetClubArticleComment.getClubArticleCommentId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticleComment deleteClubArticleComment;
    try {
      deleteClubArticleComment = clubArticleCommentRepository.findById(
          targetClubArticleComment.getClubArticleCommentId()).get();
    } catch (NoSuchElementException e) {
      resultDelete = true;
    }

    // then
    result
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultDelete).isTrue();
  }

  @Test
  void 팀_게시판_상세_페이지_댓글_삭제_예외_저자_검증_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{article_id}/comment/{comment_id}";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubDeputyLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();
    final ClubArticle targetClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());
    final ClubArticleComment targetClubArticleComment = clubArticleCommentService.getLastClubArticleComment(
        targetClubArticle.getClubArticleId());

    // when
    ResultActions result = mockMvc.perform(
        delete(url, targetClub.getClubId(), targetClubArticle.getClubArticleId(),
            targetClubArticleComment.getClubArticleCommentId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    result
        .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void 팀_비밀_게시판_생성_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{club_member_id}/save/confidential";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();

    // 생성 데이터 준비
    final String saveTargetTitle = "클럽 비밀 게시판 생성 제목 테스트";
    final String saveTargetContent = "클럽 비밀 게시판 생성 내용 테스트";
    final String saveTargetAnonymity = ANONYMITY.ANONYMOUS.getState();
    final SaveClubArticleConfidential requestDto = SaveClubArticleConfidential.builder()
        .clubArticleTitle(saveTargetTitle)
        .clubArticleContent(saveTargetContent)
        .anonymity(saveTargetAnonymity)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // then
    result
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultClubArticle.getTitle()).isEqualTo(saveTargetTitle);
    assertThat(resultClubArticle.getContent()).isEqualTo(saveTargetContent);
    assertThat(resultClubArticle.getAnonymity().getState()).isEqualTo(saveTargetAnonymity);
    assertThat(resultClubArticle.getClassification()).isEqualTo(
        CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL);
  }

  @Test
  void 팀_건의_게시판_생성_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{club_member_id}/save";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();

    // 생성 데이터 준비
    final String saveTargetTitle = "클럽 건의 게시판 생성 제목 테스트";
    final String saveTargetContent = "클럽 건의 게시판 생성 내용 테스트";
    final String saveTargetClassification = CLUB_ARTICLE_CLASSIFICATION.SUGGESTION.getSort();
    final SaveClubArticleFreeAndSuggestion requestDto = SaveClubArticleFreeAndSuggestion.builder()
        .clubArticleTitle(saveTargetTitle)
        .clubArticleContent(saveTargetContent)
        .classification(saveTargetClassification)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // then
    result
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultClubArticle.getTitle()).isEqualTo(saveTargetTitle);
    assertThat(resultClubArticle.getContent()).isEqualTo(saveTargetContent);
    assertThat(resultClubArticle.getClassification().getSort()).isEqualTo(saveTargetClassification);
  }

  @Test
  void 팀_자유_게시판_생성_테스트() throws Exception {
    // given
    // 유저 로그인
    final String token = testCreateUtil.create_token_one_club_leader_member();
    final String url = "/clubs/informations/{club_id}/articles/{club_member_id}/save";

    // 검증을 위한 데이터 준비
    final Club targetClub = clubRepository.findById(targetClubOneId).get();
    final Member targetMember = memberOneClubLeader;
    final ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(
        targetClub.getClubId(), targetMember.getMemberId()).get();

    // 생성 데이터 준비
    final String saveTargetTitle = "클럽 자유 게시판 생성 제목 테스트";
    final String saveTargetContent = "클럽 자유 게시판 생성 내용 테스트";
    final String saveTargetClassification = CLUB_ARTICLE_CLASSIFICATION.FREEDOM.getSort();
    final SaveClubArticleFreeAndSuggestion requestDto = SaveClubArticleFreeAndSuggestion.builder()
        .clubArticleTitle(saveTargetTitle)
        .clubArticleContent(saveTargetContent)
        .classification(saveTargetClassification)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(
        post(url, targetClub.getClubId(), targetClubMember.getClubMemberId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header("authorization", "Bearer " + token) // token header에 담기
    );

    final ClubArticle resultClubArticle = clubArticleService.findLastByClubArticleId(
        targetClubMember.getClubMemberId());

    // then
    result
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(resultClubArticle.getTitle()).isEqualTo(saveTargetTitle);
    assertThat(resultClubArticle.getContent()).isEqualTo(saveTargetContent);
    assertThat(resultClubArticle.getClassification().getSort()).isEqualTo(saveTargetClassification);
  }
}

