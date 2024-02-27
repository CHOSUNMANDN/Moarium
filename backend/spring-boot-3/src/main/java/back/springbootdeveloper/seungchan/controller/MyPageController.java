package back.springbootdeveloper.seungchan.controller;


import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "마이페이지", description = "마이페이지 관련 API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("clubs/personal-info/{club_member_id}")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final EntityDeleteService entityDeleteService;
    private final ClubService clubService;
    private final TokenService tokenService;
    private final ClubGradeService clubGradeService;
    private final ClubArticleCommentService clubArticleCommentService;
    private final ClubArticleService clubArticleService;
    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 - 동아리 탈퇴하기", description = "클럽 인원이 클럽을 탈퇴한다.")
    @PostMapping(value = "/quit")
    public ResponseEntity<BaseResponseBody> quitClubMember(
            @PathVariable(value = "club_member_id") Long clubMemberId,
            HttpServletRequest request) {
        // 클럽 이름
        String clubName = clubService.getClubName(clubMemberId);
        // 멤버 추방
        entityDeleteService.expulsionMemberFromClub(clubMemberId);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(RESPONSE_MESSAGE_VALUE.SUCCESS_QUIT_CLUB(clubName));
    }

    @Operation(summary = "마이페이지 - 휴면/활동 전환", description = "해당 클럽 인원이 마이페이지 휴면/활동 전환 기능")
    @PostMapping(value = "/transform")
    public ResponseEntity<BaseResponseBody> togleMemberAndDormancyClubMember(
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;

        // 휴면 등급 업데이트
        Boolean updateSuccess = clubGradeService.toggleMemberAndDormantOfClubGrade(clubMemberId);
        if (updateSuccess) {
            if (clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.MEMBER)) {
                return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_TOGLE_MEMBER_GRADE.get());
            }
            if (clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.DORMANT)) {
                return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_TOGLE_DORMANT_GRADE.get());
            }
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_TOGLE_GRADE.get());
    }


    @Operation(summary = "마이페이지 - 내가 쓴 댓글 보기", description = "해당 클럽 인원이 마이페이지에서 내가 쓴 댓글 보기")
    @GetMapping(value = "/comments")
    @ResponseBody
    public BaseResultDTO<ClubMemberCommentsResDto> findAllClubMemberComments(
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;

        ClubMemberCommentsResDto clubMemberCommentsResDto = clubArticleCommentService.getClubMemberCommentsResDto(clubMemberId);

        return BaseResultDTO.ofSuccess(clubMemberCommentsResDto);
    }

    @Operation(summary = "마이페이지 - 내가 쓴 글 보기", description = "해당 클럽 인원이 마이페이지에서 내가 쓴 글 보기")
    @GetMapping(value = "/articles")
    @ResponseBody
    public BaseResultDTO<ClubMemberArticlesResDto> findAllClubMemberArticles(
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        ClubMemberArticlesResDto clubMemberCommentsResDto = clubArticleService.getClubMemberArticlesResDto(clubMemberId);

        return BaseResultDTO.ofSuccess(clubMemberCommentsResDto);
    }

    @Operation(summary = "마이페이지 - 전체 출석 현황", description = "현제 회원이 참여한 클럽의 출석 정보 확인 가능")
    @GetMapping(value = "/attendances")
    @ResponseBody
    public BaseResultDTO<MyAllClubMembersInformationsResDto> findAllClubMemberAttendanceInformation(
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        List<MyAllClubMembersAttendance> myAllClubMembersAttendances = myPageService.getMyAllClubMembersAttendance(memberId, clubMemberId);

        return BaseResultDTO.ofSuccess(MyAllClubMembersInformationsResDto.builder()
                .myAllClubMembersAttendances(myAllClubMembersAttendances)
                .build());
    }

    // TODO: 2/26/24 향후 디비 수정 
    @Operation(summary = "마이 페이지", description = "현제 회원 MyPage")
    @GetMapping(value = "")
    @ResponseBody
    public BaseResultDTO<MyPageClubMemberInformationResDto> findMyPageClubMemberInformation(
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        MyPageClubMemberInformationResDto myPageClubMemberInformationResDto = myPageService.getMyPageClubMemberInformationResDto(clubMemberId);

        return BaseResultDTO.ofSuccess(myPageClubMemberInformationResDto);
    }
}
