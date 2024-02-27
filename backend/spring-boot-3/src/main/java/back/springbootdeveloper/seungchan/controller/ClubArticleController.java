package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.dto.request.WriteSuggestionAnswerReqDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.service.ClubArticleCommentService;
import back.springbootdeveloper.seungchan.service.ClubArticleService;
import back.springbootdeveloper.seungchan.service.ClubGradeService;
import back.springbootdeveloper.seungchan.service.EntityApplyService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "팀 게시판 - 수정 API", description = "글 작성자의 게시물을 업데이트한다.")
    @PutMapping(value = "/{article_id}")
    public ResponseEntity<BaseResponseBody> updateClubArticle(
            @RequestBody @Valid UpdateClubArticlePutDto updateClubArticlePutDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        if (clubArticleService.isAuthor(memberId, clubId, articleId)) {
            clubArticleService.updateClubArticle(clubId, memberId, articleId, updateClubArticlePutDto);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.UPDATE_CLUB_ARTICLE.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_UPDATE_CLUB_ARTICLE.get());
    }

    @Operation(summary = "팀 게시판 - 삭제 API", description = "글 작성자의 게시물을 삭제 한다.")
    @DeleteMapping(value = "/{article_id}")
    public ResponseEntity<BaseResponseBody> deleteClubArticle(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        if (clubArticleService.isAuthor(memberId, clubId, articleId)) {
            clubArticleService.deleteClubArticle(clubId, memberId, articleId);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_DELETE_CLUB_ARTICLE.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_DELETE_CLUB_ARTICLE.get());
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 조회", description = "해당 club 게시판의 상세 페이지 조회")
    @GetMapping(value = "/{article_id}")
    public BaseResultDTO<ClubArticleDetailResDto> findClubArticleDetail(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        final ClubArticleDetailResDto clubArticleDetailResDto = clubArticleService.getClubArticleDetailResDto(clubId, articleId, memberId);

        return BaseResultDTO.ofSuccess(clubArticleDetailResDto);
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 좋아요", description = "해당 club 게시판의 좋아요 표기")
    @PostMapping(value = "/{article_id}/like")
    public ResponseEntity<BaseResponseBody> addClubArticleLikeCount(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        if (clubArticleService.addLikeCountClubArticle(articleId)) {
            return BaseResponseBodyUtiil.BaseResponseBodySuccess();
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure();
    }

    @Operation(summary = "팀 건의 게시판 - 전체 조회", description = "팀 건 게시판 전체 조회")
    @GetMapping(value = "/suggestions")
    public BaseResultDTO<ClubArticleSimpleInformationResDto> findAllSuggestionClubArticle(
            @PathVariable(value = "club_id") Long clubId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubArticleSimpleInformationResDto clubMemberSimpleInformationResDto = clubArticleService.getClubMemberSimpleInformationResDto(clubId, memberId, CLUB_ARTICLE_CLASSIFICATION.SUGGESTION);

        return BaseResultDTO.ofSuccess(clubMemberSimpleInformationResDto);
    }

    @Operation(summary = "팀 비밀 게시판 - 전체 조회", description = "팀 비밀 게시판 전체 조회")
    @GetMapping(value = "/confidentials")
    public BaseResultDTO<ClubArticleSimpleInformationResDto> findAllConfidentialClubArticle(
            @PathVariable(value = "club_id") Long clubId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubArticleSimpleInformationResDto clubMemberSimpleInformationResDto =
                clubArticleService.getClubMemberSimpleInformationResDto(clubId, memberId, CLUB_ARTICLE_CLASSIFICATION.CONFIDENTIAL);

        return BaseResultDTO.ofSuccess(clubMemberSimpleInformationResDto);
    }

    @Operation(summary = "팀 자유 게시판 - 전체 조회", description = "팀 자유 게시판 전체 조회")
    @GetMapping(value = "/frees")
    public BaseResultDTO<ClubArticleSimpleInformationResDto> findSuggestionAnswerClubArticle(
            @PathVariable(value = "club_id") Long clubId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubArticleSimpleInformationResDto clubMemberSimpleInformationResDto =
                clubArticleService.getClubMemberSimpleInformationResDto(clubId, memberId, CLUB_ARTICLE_CLASSIFICATION.FREEDOM);

        return BaseResultDTO.ofSuccess(clubMemberSimpleInformationResDto);
    }


    @Operation(summary = "팀 건의 게시판 답변 조회", description = "팀 건의 게시판 답변 조회")
    @GetMapping(value = "/{article_id}/answer")
    public BaseResultDTO<ClubArticleAnswerResDto> findSuggestionAnswerClubArticle(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubArticleAnswerResDto clubArticleAnswerResDto =
                clubArticleService.getClubArticleAnswerResDto(clubId, memberId, articleId);

        return BaseResultDTO.ofSuccess(clubArticleAnswerResDto);
    }


    @Operation(summary = "팀 건의 게시판 - 상세 페이지 - 답변 쓰기", description = "팀 건의 게시판 답변 쓰기")
    @PostMapping(value = "/{article_id}/answer/write")
    public ResponseEntity<BaseResponseBody> writeSuggestionAnswerClubArticle(
            @RequestBody @Valid WriteSuggestionAnswerReqDto writeSuggestionAnswerReqDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
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
            @RequestBody @Valid WriteClubArticleCommentReqDto writeClubArticleCommentReqDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;

        String clubArticleCommentContent = writeClubArticleCommentReqDto.getClubArticleCommentContent();
        String anonymity = writeClubArticleCommentReqDto.getAnonymity();
        entityApplyService.addClubArticleComment2ClubArticle(articleId, memberId, clubArticleCommentContent, anonymity)
                .orElseThrow(EntityNotFoundException::new);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 댓글 삭제", description = "팀 게시판 - 상세 페이지 - 댓글 삭제")
    @DeleteMapping(value = "/{article_id}/comment/{comment_id}")
    public ResponseEntity<BaseResponseBody> deleteClubArticleComment(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId,
            @PathVariable(value = "comment_id") Long commentId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;

        final Boolean isAuthorClubArticleComment = clubArticleCommentService.isAuthor(memberId, commentId);
        if (isAuthorClubArticleComment) {
            clubArticleCommentService.deleteByCommentId(commentId);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess();
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure();
    }
}
