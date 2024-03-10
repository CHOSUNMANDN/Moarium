package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE_VALUE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_GRADE;
import back.springbootdeveloper.seungchan.dto.request.CheckDuplicationClubNameReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.service.ClubMemberInformationService;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.service.EntityApplyService;
import back.springbootdeveloper.seungchan.service.MemberService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "스터디 팀을 등록하는 컨트롤러", description = "팀 등록 관련 관리 클래스")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/main/club/apply")
public class ClubRegistrationController {

  private final EntityApplyService entityApplyService;
  private final TokenService tokenService;
  private final MemberService memberService;
  private final ClubMemberInformationService clubMemberInformationService;
  private final ClubService clubService;

  @Operation(summary = "개인 회원 - 팀 등록 api")
  @PostMapping("")
  public ResponseEntity<BaseResponseBody> getClubRegistraionInfo(
      HttpServletRequest request,
      @RequestParam(value = "clubName") String clubName,
      @RequestParam(value = "clubIntroduction") String clubIntroduction,
      @RequestParam(value = "clubProfileImages", required = false) MultipartFile clubProfileImage,
      @RequestParam(name = "clubInformationImages", required = false) List<MultipartFile> clubInformationImages) {
    Long memberId = tokenService.getMemberIdFromToken(request);
    Member myMember = memberService.findByMemberId(memberId);

    // clubName이 빈 문자열인 경우에 대한 예외 처리
    if (clubName.isBlank()) {
      return BaseResponseBodyUtiil.BaseResponseBodyFailure(
          ResponseMessage.BAD_NOT_BLANK_CLUB_NAME.get());
    }

    // clubIntroduction이 빈 문자열인 경우에 대한 예외 처리
    if (clubIntroduction.isBlank()) {
      return BaseResponseBodyUtiil.BaseResponseBodyFailure(
          ResponseMessage.BAD_NOT_BLANK_CLUB_INTRODUCTION.get());
    }

    Club saveClub = entityApplyService.makeClub(
        clubName.trim(), clubIntroduction, clubProfileImage, clubInformationImages
    ).orElseThrow(EntityNotFoundException::new);

    ClubMemberInformation clubMemberInformation = clubMemberInformationService.makeLeaderClubMemberInformation(
        saveClub, myMember);

    ClubMember applyClubMember = entityApplyService.applyLeaderClub(myMember, saveClub,
        CLUB_GRADE.LEADER,
        clubMemberInformation).orElseThrow(EntityNotFoundException::new);

    if (saveClub != null) {
      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          ResponseMessage.SUCCESS_REGISTRATION_CLUB.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodyFailure(
        ResponseMessage.BAD_REGISTRATION_CLUB.get());
  }

  @Operation(summary = "개인 회원 - 팀 등록 팀 이름 중복 확인 ")
  @PostMapping("/overlap")
  public ResponseEntity<BaseResponseBody> checkDuplicationClubName(
      @RequestBody @Valid CheckDuplicationClubNameReqDto checkDuplicationClubNameReqDto,
      HttpServletRequest request) {
    String targetClubName = checkDuplicationClubNameReqDto.getClubName().trim();
    Boolean isDuplicationClubName = clubService.isDuplicationClubName(targetClubName);

    if (isDuplicationClubName) {
      return BaseResponseBodyUtiil.BaseResponseBodyFailure(
          ResponseMessage.BAD_DUPLICATION_CLUBNAME.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodySuccess(
        RESPONSE_MESSAGE_VALUE.SUCCESS_NOT_DUPLICATION_CLUBNAME(targetClubName));
  }
}
