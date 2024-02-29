package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.constant.entity.POSSIBLE_STATUS;
import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberReqDto;
import back.springbootdeveloper.seungchan.dto.request.GiveVacationTokenReqDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.AttendanceWeek;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import back.springbootdeveloper.seungchan.util.DayUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "club detail page 컨트롤러", description = "club datail page 컨트롤러 - 토큰 필요")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations/{club_id}/details")
@RequiredArgsConstructor
public class ClubDetailPageController {
    private final ClubDetailPageService clubDetailPageService;
    private final MemberService memberService;
    private final VacationTokenService vacationTokenService;
    private final ClubGradeService clubGradeService;
    private final EntityDeleteService entityDeleteService;
    private final AttendanceNumberService attendanceNumberService;
    private final AttendanceWeekDateService attendanceWeekDateService;
    private final TokenService tokenService;
    private final AttendanceWeekService attendanceWeekService;

    @Operation(summary = "회원 휴먼 페이지 조회", description = "해당 클럽의 휴먼 회원들 조회")
    @GetMapping(value = "/dormancys")
    public BaseResultDTO<DormancysMembersResDto> getDormancysMemberPage(HttpServletRequest request, @PathVariable(value = "club_id") Long clubId) {
        List<String> allDormancyMemberNamesOfClub = clubDetailPageService.getAllDormancyMemberNamesOfClub(clubId);
        DormancysMembersResDto dormancysMembersResDto = DormancysMembersResDto.builder()
                .dormancyMembers(allDormancyMemberNamesOfClub)
                .build();

        return BaseResultDTO.ofSuccess(dormancysMembersResDto);
    }

    @Operation(summary = "동아리 상세 페이지 조회", description = "동아리 휴먼 회원들 제외한 모든 회원들 상세 조회 가능")
    @GetMapping(value = "")
    public BaseResultDTO<ClubMemberDetailResDto> getMemberDetailsPage(HttpServletRequest request, @PathVariable(value = "club_id") Long clubId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        ClubGrade myClubGrade = clubGradeService.findByClubIdAndMemberId(clubId, memberId);
        // 동아리 회원의 상세 페이지 조회
        ClubMemberDetailResDto clubMemberResponse = clubDetailPageService.getClubMemberResponse(clubId, memberId, myClubGrade.getClubGrade());

        return BaseResultDTO.ofSuccess(clubMemberResponse);
    }

    @Operation(summary = "회원 상세 조회", description = "동아리 특정 회원 정보 상세 조회 가능")
    @GetMapping(value = "/{club_member_id}")
    public BaseResultDTO<ClubMemberInformationResDto> getClubMemberDetails(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {

        ClubMemberInformationResDto clubMemberResponse = clubDetailPageService.getClubMemberInformationResDto(clubMemberId);

        return BaseResultDTO.ofSuccess(clubMemberResponse);
    }

    @Operation(summary = "동아리 소개 페이지 - 휴가 제공 API", description = "동아리 대표의 동아리 회원에게 휴가 제공을 해주는 API 제공")
    @PostMapping(value = "/{club_member_id}/vacation")
    public ResponseEntity<BaseResponseBody> giveVacationTokenToClubMember(
            HttpServletRequest request,
            @RequestBody @Valid GiveVacationTokenReqDto giveVacationTokenReqDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        Long memberLeaderId = tokenService.getMemberIdFromToken(request);

        Integer vacationToken = giveVacationTokenReqDto.getVacationToken();
        Member targetMember = memberService.findByClubMemberId(clubMemberId);

        // 로그인 대상 대표 검증 하는 검증 메서드
        Boolean isLeaderClub = clubGradeService.isMemberStatus(clubId, memberLeaderId, CLUB_GRADE.LEADER);
        if (!isLeaderClub) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_NOT_LEADER_CLUB.get());
        }

        // 요청한 휴가 갯수에 따른 휴가 갯수 업데이트
        Boolean updateSuccess = vacationTokenService.updateVacationToken(clubMemberId, vacationToken);

        if (updateSuccess) {
            return BaseResponseBodyUtiil.BaseResponseBodySuccess(RESPONSE_MESSAGE_VALUE.SUCCESS_UPDATE_VACATION_TOKEN(targetMember.getFullName(), vacationToken));
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(RESPONSE_MESSAGE_VALUE.FAIL_UPDATE_VACATION_TOKEN(targetMember.getFullName()));
    }

    @Operation(summary = "동아리 소개 페이지 - 회원 추방 API", description = "동아리 대표가 동아리 회원을 추방 시킨다.")
    @PostMapping(value = "/{club_member_id}/expulsion")
    public ResponseEntity<BaseResponseBody> expulsionClubMember(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        Long memberLeaderId = tokenService.getMemberIdFromToken(request);
        Member targetMember = memberService.findByClubMemberId(clubMemberId);

        // 로그인 대상 대표 검증 하는 검증 메서드
        Boolean isLeaderClub = clubGradeService.isMemberStatus(clubId, memberLeaderId, CLUB_GRADE.LEADER);
        if (!isLeaderClub) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_NOT_LEADER_CLUB.get());
        }

        // 대상이 대표확인
        Boolean isTargetLeaderClub = clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.LEADER);
        if (isTargetLeaderClub) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_TARGET_LEADER_MEMBER.get());
        }

        // 멤버 추방
        entityDeleteService.expulsionMemberFromClub(clubMemberId);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(RESPONSE_MESSAGE_VALUE.SUCCESS_EXPULSION_MEMBER(targetMember.getFullName()));
    }


    @Operation(summary = "동아리 소개 페이지 - 회원 휴먼 전환 API", description = "동아리 대표가 동아리 회원을 휴면으로 변경")
    @PostMapping(value = "/{club_member_id}/dormancy")
    public ResponseEntity<BaseResponseBody> dormancyClubMember(
            HttpServletRequest request,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        Long memberLeaderId = tokenService.getMemberIdFromToken(request);
        Member memberLeader = memberService.findByMemberId(memberLeaderId);
        Member targetMember = memberService.findByClubMemberId(clubMemberId);

        // 휴면 멤버 여부
        Boolean alreadyDormant = clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.DORMANT);
        if (alreadyDormant) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(RESPONSE_MESSAGE_VALUE.ALREADY_DORMANT_MEMBER(targetMember.getFullName()));
        }

        // 로그인 대상 대표 검증 하는 검증 메서드
        Boolean isLeaderClub = clubGradeService.isMemberStatus(clubId, memberLeaderId, CLUB_GRADE.LEADER);
        if (!isLeaderClub) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_NOT_LEADER_CLUB.get());
        }

        // 대상이 대표확인
        Boolean isTargetLeaderClub = clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.LEADER);
        if (isTargetLeaderClub) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_TARGET_LEADER_MEMBER.get());
        }


        // 휴면 등급 업데이트
        Boolean updateSuccess = clubGradeService.updateClubGradeOfClubMember(clubMemberId, CLUB_GRADE.DORMANT);
        if (updateSuccess) {
            return BaseResponseBodyUtiil.BaseResponseBodySuccess(RESPONSE_MESSAGE_VALUE.SUCCESS_UPDATE_CLUB_GRADE(targetMember.getFullName()));
        }

        return BaseResponseBodyUtiil.BaseResponseBodyFailure(RESPONSE_MESSAGE_VALUE.FAIL_UPDATE_CLUB_GRADE(targetMember.getFullName()));
    }

    @Operation(summary = "출석번호 입력 API", description = "동아리 회원의 출석번호 입력")
    @PostMapping(value = "/{club_member_id}/attendance")
    public ResponseEntity<BaseResponseBody> checkAttendanceNumber(
            HttpServletRequest request,
            @RequestBody @Valid AttendanceNumberReqDto attendanceNumberReqDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "club_member_id") Long clubMemberId) {
        Long memberId = tokenService.getMemberIdFromToken(request);

        // 출석번호 확인
        Boolean isDormantMember = clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.DORMANT);
        Boolean isPassTodayAttendance = attendanceNumberService.checkAttendanceNumber(clubId, attendanceNumberReqDto.getNumOfAttendance());
        Boolean isPossibleAttendance = attendanceWeekDateService.isPossibleUpdateAttendanceState(clubId, memberId);

        // 클럽 지정 출석을 체크
        String clubPossibleStatus = attendanceWeekService.getTodayPossibleStatus(clubId, DayUtil.getTodayDayOfWeek());
        if (POSSIBLE_STATUS.POSSIBLE.isNot(clubPossibleStatus)) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_REQUEST_NOT_CLUB_CHECK_STATE.get());
        }

        // 로그인 한 회원이 타겟의 유저인지 확인
        Boolean isSameMemberTargetAndLogin = memberService.isSameTargetAndLoginMember(memberId, clubMemberId);
        if (!isSameMemberTargetAndLogin) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_NOT_SAME_LOGIN_TARGET_MEMBER.get());
        }

        // 휴면 계정 확인
        if (isDormantMember) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_DORMANT_TODAY_ATTENDANCE_STATE.get());
        }

        // 출석 가능 확인
        if (!isPossibleAttendance) {
            return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_ALREADY_TODAY_UPDATE_ATTENDANCE_STATE.get());
        }

        if (isPassTodayAttendance) {
            // 출석 상태 전환
            attendanceWeekDateService.updateTodayAttendanceWeekDate(clubMemberId, ATTENDANCE_STATE.ATTENDANCE);
            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.PASS_TODAY_ATTENDANCE.get());
        }

        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_TODAY_ATTENDANCE.get());
    }
}