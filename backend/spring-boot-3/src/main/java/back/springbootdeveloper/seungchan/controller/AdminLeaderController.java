package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.TempMembersDetailInformationResDto;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformationResDto;
import back.springbootdeveloper.seungchan.dto.response.TempMembersInformation;
import back.springbootdeveloper.seungchan.service.ClubGradeService;
import back.springbootdeveloper.seungchan.service.TempMemberService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import back.springbootdeveloper.seungchan.util.MyValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Club Leader을 위한 Admin 컨트롤러", description = "팀의 대표의 클럽 환경 설정을 기능의 페이지 이다.")
@RestController
@RequestMapping("/clubs/informations/{club_id}/details/leader")
@RequiredArgsConstructor
public class AdminLeaderController {

  private final TokenService tokenService;
  private final TempMemberService tempMemberService;
  private final ClubGradeService clubGradeService;

  @Operation(summary = "모든 신청 신입 회원 정보 확인", description = "팀 리더 admin Control 페이지 모든 신청 신입 회원들의 정보 보여 확인")
  @GetMapping("/temp/member")
  public BaseResultDTO<TempMembersInformationResDto> findAllTempMembersForClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId) {
    // 클럽의 리더 검증
    MyValidation.isLeaderMember(tokenService, request, clubId);
    // 필요한 정보 찾기
    List<TempMembersInformation> tempMembersInformations = tempMemberService.findAllTempMemberInformation(
        clubId);

    return BaseResultDTO.ofSuccess(TempMembersInformationResDto.builder()
        .tempMembersInformations(tempMembersInformations)
        .build());
  }

  @Operation(summary = "개인 신청 신입 회원 정보 확인", description = "팀 리더 admin Control 페이지 개인 신청 신입 회원들의 정보 보여 확인")
  @GetMapping("/temp/member/{club_member_id}")
  public BaseResultDTO<TempMembersDetailInformationResDto> findDetailTempMembersForClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId,
      @PathVariable(value = "club_member_id") Long clubMemberId) {
    // 클럽의 리더 검증
    MyValidation.isLeaderMember(tokenService, request, clubId);
    // 필요한 정보 찾기
    TempMembersDetailInformationResDto tempMembersDetailInformationResDto = tempMemberService.
        getTempMembersDetailInformationResDto(clubMemberId);

    return BaseResultDTO.ofSuccess(tempMembersDetailInformationResDto);
  }

  @Operation(summary = "신청 신입 회원 정회원 전환", description = "팀 리더 admin Control 페이지 신청 신입 회원의 일반 회원으로 전환")
  @PostMapping("/temp/member/{club_member_id}/accept")
  public ResponseEntity<BaseResponseBody> acceptTempMembersForClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId,
      @PathVariable(value = "club_member_id") Long clubMemberId) {
    // 클럽의 리더 검증
    MyValidation.isLeaderMember(tokenService, request, clubId);

    // 임시 회원 검증
    Boolean isTempMember = clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.TEMP_MEMBER);
    if (isTempMember) {
      tempMemberService.acceptTempMember(clubMemberId);

      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          ResponseMessage.SUCCESS_ACCEPT_TEMP_MEMBER.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodyFailure(
        ResponseMessage.BAD_ACCEPT_TEMP_MEMBER.get());
  }

  @Operation(summary = "신청 신입 회원 거절", description = "팀 리더 admin Control 페이지 신청 신입 회원 거절")
  @PostMapping("/temp/member/{club_member_id}/refuse")
  public ResponseEntity<BaseResponseBody> refuseTempMembersForClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId,
      @PathVariable(value = "club_member_id") Long clubMemberId) {
    // 클럽의 리더 검증
    MyValidation.isLeaderMember(tokenService, request, clubId);

    // 임시 회원 검증
    Boolean isTempMember = clubGradeService.isMemberStatus(clubMemberId, CLUB_GRADE.TEMP_MEMBER);
    if (isTempMember) {
      tempMemberService.refuseTempMember(clubMemberId);

      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          ResponseMessage.SUCCESS_REFUSE_TEMP_MEMBER.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodyFailure(
        ResponseMessage.BAD_REFUSE_TEMP_MEMBER.get());
  }
}