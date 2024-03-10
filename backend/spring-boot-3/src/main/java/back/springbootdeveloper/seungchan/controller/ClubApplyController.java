package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.ApplyMemberToClubReqDto;
import back.springbootdeveloper.seungchan.dto.request.CustomInformationReqForm;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.CustomInformation;
import back.springbootdeveloper.seungchan.dto.response.MemberApplyClubResDto;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.service.ClubMemberService;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.service.CustomClubApplyInformationService;
import back.springbootdeveloper.seungchan.service.EntityApplyService;
import back.springbootdeveloper.seungchan.service.MemberService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원이 스터디 팀에 등록하는 컨트롤러", description = "회원의 팀 등록 관련 관리 클래스")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/{club_id}/apply")
public class ClubApplyController {

  private final EntityApplyService entityApplyService;
  private final CustomClubApplyInformationService customClubApplyInformationService;
  private final ClubService clubService;
  private final TokenService tokenService;
  private final MemberService memberService;
  private final ClubMemberService clubMemberService;

  @Operation(summary = "동아리 지원서 작성 API")
  @PostMapping("")
  public ResponseEntity<BaseResponseBody> applyMemberToClub(
      @RequestBody @Valid ApplyMemberToClubReqDto applyMemberToClubReqDto,
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId) {
    Long loginMemberId = tokenService.getMemberIdFromToken(request);
    Member loginMember = memberService.findByMemberId(loginMemberId);
    Club targetClub = clubService.findByClubId(clubId);

    // 이미 클럽에 가입되어 있는 경우
    Boolean isAlreadyRegistration2Club = clubMemberService.isAlreadyRegistration2Club(targetClub,
        loginMember);
    if (isAlreadyRegistration2Club) {
      return BaseResponseBodyUtiil.BaseResponseBodyFailure(
          ResponseMessage.BAD_ALREADY_REGISTRATION_CLUB.get());
    }

    // 신청 두번 한 경우
    Boolean isAlreadyApply2Club = clubMemberService.isAlreadyApply2Club(targetClub, loginMember,
        CLUB_GRADE.TEMP_MEMBER);
    if (isAlreadyApply2Club) {
      return BaseResponseBodyUtiil.BaseResponseBodyFailure(
          ResponseMessage.BAD_ALREADY_APPLY_CLUB.get());
    }

    entityApplyService.applyTempMemberClub(loginMember, targetClub, CLUB_GRADE.TEMP_MEMBER,
        applyMemberToClubReqDto);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_APPLY_CLUB.get());
  }

  @Operation(summary = "동아리 지원서 작성 API - 커스텀 조회")
  @GetMapping("/find")
  public BaseResultDTO<MemberApplyClubResDto> findMemberApplyClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId) {

    List<CustomInformation> customInformations = customClubApplyInformationService
        .findAllCustomInformationByClubId(clubId);
    String clubName = clubService.getClubNameByClubId(clubId);

    return BaseResultDTO.ofSuccess(MemberApplyClubResDto.builder()
        .clubName(clubName)
        .customInformations(customInformations)
        .build());
  }
}
