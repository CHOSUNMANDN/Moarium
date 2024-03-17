package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.ApplyMemberToClubReqDto;
import back.springbootdeveloper.seungchan.dto.response.ClubIntroductionInformationResDto;
import back.springbootdeveloper.seungchan.service.ClubIntroductionInformationService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클럽 소개 조회 페이지", description = "클럽에 지원하기 위해서 클럽 소개 조회 페이지")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations/{club_id}")
@RequiredArgsConstructor
public class ClubIntroductionInformationController {

  private final TokenService tokenService;
  private final ClubIntroductionInformationService clubIntroductionInformationService;

  @Operation(summary = "동아리 지원서 작성 API")
  @GetMapping("")
  public BaseResultDTO<ClubIntroductionInformationResDto> applyMemberToClub(
      HttpServletRequest request,
      @PathVariable(value = "club_id") Long clubId) {
    ClubIntroductionInformationResDto clubIntroductionInformationResDto =
        clubIntroductionInformationService.getClubIntroductionInformationResDto(
            clubId);

    return BaseResultDTO.ofSuccess(clubIntroductionInformationResDto);
  }
}
