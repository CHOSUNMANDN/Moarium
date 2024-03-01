package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.SaveClubArticleConfidential;
import back.springbootdeveloper.seungchan.dto.request.SaveClubArticleFreeAndSuggestion;
import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.dto.request.WriteSuggestionAnswerReqDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club Article 게시물 관련 Controller", description = "Club Article 관련 API을 관리한다.")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations/{club_id}/articles")
@RequiredArgsConstructor
public class ClubArticleController {
    private final ClubArticleService clubArticleService;
    private final ClubGradeService clubGradeService;
    private final EntityApplyService entityApplyService;
    private final ClubArticleCommentService clubArticleCommentService;
    private final TokenService tokenService;

    @Operation(summary = "팀 게시판 - 수정 API", description = "글 작성자의 게시물을 업데이트한다.")
    @PutMapping(value = "/{article_id}")
    public ResponseEntity<BaseResponseBody> updateClubArticle(
            HttpServletRequest request,
            @RequestBody @Valid UpdateClubArticlePutDto updateClubArticlePutDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        if (clubArticleService.isAuthor(memberId, clubId, articleId)) {
            clubArticleService.updateClubArticle(clubId, memberId, articleId, updateClubArticlePutDto);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.UPDATE_CLUB_ARTICLE.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_UPDATE_CLUB_ARTICLE.get());
    }

    @Operation(summary = "팀 게시판 - 삭제 API", description = "글 작성자의 게시물을 삭제 한다.")
    @DeleteMapping(value = "/{article_id}")
    public ResponseEntity<BaseResponseBody> deleteClubArticle(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        if (clubArticleService.isAuthor(memberId, clubId, articleId)) {
            clubArticleService.deleteClubArticle(clubId, memberId, articleId);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_DELETE_CLUB_ARTICLE.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_DELETE_CLUB_ARTICLE.get());
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 조회", description = "해당 club 게시판의 상세 페이지 조회")
    @GetMapping(value = "/{article_id}")
    public BaseResultDTO<ClubArticleDetailResDto> findClubArticleDetail(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        final ClubArticleDetailResDto clubArticleDetailResDto = clubArticleService.getClubArticleDetailResDto(clubId, articleId, memberId);

        return BaseResultDTO.ofSuccess(clubArticleDetailResDto);
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 좋아요", description = "해당 club 게시판의 좋아요 표기")
    @PostMapping(value = "/{article_id}/like")
    public ResponseEntity<BaseResponseBody> addClubArticleLikeCount(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {

        if (clubArticleService.addLikeCountClubArticle(articleId)) {
            return BaseResponseBodyUtiil.BaseResponseBodySuccess();
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure();
    }

    @Operation(summary = "팀 건의 게시판 - 전체 조회", description = "팀 건 게시판 전체 조회")
    @GetMapping(value = "/suggestions")
    public BaseResultDTO<ClubArticleSimpleInformationResDto> findAllSuggestionClubArticle(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        ClubArticleSimpleInformationResDto clubMemberSimpleInformationResDto = clubArticleService.getClubMemberSimpleInformationResDto(clubId, memberId, CLUB_ARTICLE_CLASSIFICATION.SUGGESTION);

        return BaseResultDTO.ofSuccess(clubMemberSimpleInformationResDto);
    }

    @Operation(summary = "팀 비밀 게시판 - 전체 조회", description = "팀 비밀 게시판 전체 조회")
    @GetMapping(value = "/confidentials")
    public BaseResultDTO<ClubArticleSimpleInformationResDto> findAllConfidentialClubArticle(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        ClubArticleSimpleInformationResDto clubMemberSimpleInformationResDto =
                clubArticleService.getClubMemberSimpleInformationResDto(clubId, memberId, CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL);

        return BaseResultDTO.ofSuccess(clubMemberSimpleInformationResDto);
    }

    @Operation(summary = "팀 자유 게시판 - 전체 조회", description = "팀 자유 게시판 전체 조회")
    @GetMapping(value = "/frees")
    public BaseResultDTO<ClubArticleSimpleInformationResDto> findSuggestionAnswerClubArticle(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        ClubArticleSimpleInformationResDto clubMemberSimpleInformationResDto =
                clubArticleService.getClubMemberSimpleInformationResDto(clubId, memberId, CLUB_ARTICLE_CLASSIFICATION.FREEDOM);

        return BaseResultDTO.ofSuccess(clubMemberSimpleInformationResDto);
    }


    @Operation(summary = "팀 건의 게시판 답변 조회", description = "팀 건의 게시판 답변 조회")
    @GetMapping(value = "/{article_id}/answer")
    public BaseResultDTO<ClubArticleAnswerResDto> findSuggestionAnswerClubArticle(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        ClubArticleAnswerResDto clubArticleAnswerResDto =
                clubArticleService.getClubArticleAnswerResDto(clubId, memberId, articleId);

        return BaseResultDTO.ofSuccess(clubArticleAnswerResDto);
    }


    @Operation(summary = "팀 건의 게시판 - 상세 페이지 - 답변 쓰기", description = "팀 건의 게시판 답변 쓰기")
    @PostMapping(value = "/{article_id}/answer/write")
    public ResponseEntity<BaseResponseBody> writeSuggestionAnswerClubArticle(
            HttpServletRequest request,
            @RequestBody @Valid WriteSuggestionAnswerReqDto writeSuggestionAnswerReqDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        Boolean isClubLeader = clubGradeService.isMemberStatus(clubId, memberId, CLUB_GRADE.LEADER);
        Boolean isClubDeputyLeader = clubGradeService.isMemberStatus(clubId, memberId, CLUB_GRADE.DEPUTY_LEADER);

        // 클럽의 대표, 부대표 확인
        if (isClubLeader || isClubDeputyLeader) {
            clubArticleService.updateSuggestionAnswerClubArticle(articleId, writeSuggestionAnswerReqDto);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_SUGGESTION_ANSWER.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_SUGGESTION_ANSWER.get());
    }


    @Operation(summary = "팀 게시판 - 상세 페이지 - 댓글 쓰기", description = "팀 게시판 - 상세 페이지 - 댓글 쓰기")
    @PostMapping(value = "/{article_id}/comment/write")
    public ResponseEntity<BaseResponseBody> writeClubArticleComment(
            HttpServletRequest request,
            @RequestBody @Valid WriteClubArticleCommentReqDto writeClubArticleCommentReqDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        String clubArticleCommentContent = writeClubArticleCommentReqDto.getClubArticleCommentContent();
        String anonymity = writeClubArticleCommentReqDto.getAnonymity();
        entityApplyService.addClubArticleComment2ClubArticle(articleId, memberId, clubArticleCommentContent, anonymity)
                .orElseThrow(EntityNotFoundException::new);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 댓글 삭제", description = "팀 게시판 - 상세 페이지 - 댓글 삭제")
    @DeleteMapping(value = "/{article_id}/comment/{comment_id}")
    public ResponseEntity<BaseResponseBody> deleteClubArticleComment(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId,
            @PathVariable(value = "comment_id") Long commentId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        final Boolean isAuthorClubArticleComment = clubArticleCommentService.isAuthor(memberId, commentId);
        if (isAuthorClubArticleComment) {
            clubArticleCommentService.deleteByCommentId(commentId);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess();
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure();
    }

    @Operation(summary = "팀 비밀 게시판 - 생성 API", description = "팀 비밀 게시판 - 생성 API")
    @PostMapping(value = "/{club_member_id}/save/confidential")
    public ResponseEntity<BaseResponseBody> saveClubArticleConfidential(
            HttpServletRequest request,
            @RequestBody @Valid SaveClubArticleConfidential saveClubArticleConfidential,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {

        String title = saveClubArticleConfidential.getClubArticleTitle();
        String content = saveClubArticleConfidential.getClubArticleContent();
        String anonymity = saveClubArticleConfidential.getAnonymity();
        entityApplyService.applyClubArticleConfidential(title, content, clubMemberId, anonymity);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @Operation(summary = "팀 건의, 자유 게시판 - 생성 API", description = "팀 건의, 자유 게시판 - 생성 API")
    @PostMapping(value = "/{club_member_id}/save")
    public ResponseEntity<BaseResponseBody> saveClubArticleFreeAndSuggestion(
            HttpServletRequest request,
            @RequestBody @Valid SaveClubArticleFreeAndSuggestion saveClubArticleFreeAndSuggestion,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        String title = saveClubArticleFreeAndSuggestion.getClubArticleTitle();
        String content = saveClubArticleFreeAndSuggestion.getClubArticleContent();
        String classification = saveClubArticleFreeAndSuggestion.getClassification();
        entityApplyService.applyClubArticleFreeAndSuggestion(title, content, clubMemberId, classification);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }
}
