package back.springbootdeveloper.seungchan.controller;


import back.springbootdeveloper.seungchan.dto.response.ClubFindInformation;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberDetailResDto;
import back.springbootdeveloper.seungchan.dto.response.MainClubFindResDto;
import back.springbootdeveloper.seungchan.entity.ClubGrade;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main page의 Controller", description = "로그인 이후의 페이지를 담당한다.")
@RestController
@RequestMapping(value = "/main")
@RequiredArgsConstructor
public class MainController {

  private final TokenService tokenService;
  private final ClubService clubService;

  @Operation(summary = "", description = "동아리 휴먼 회원들 제외한 모든 회원들 상세 조회 가능")
  @GetMapping(value = "")
  public BaseResultDTO<MainClubFindResDto> getMemberDetailsPage(HttpServletRequest request) {
    Long loginMemberId = tokenService.getMemberIdFromToken(request);

    List<ClubFindInformation> clubFindInformations = clubService.getClubFindInformations(
        loginMemberId);

    return BaseResultDTO.ofSuccess(MainClubFindResDto.builder()
        .clubFindInformations(clubFindInformations)
        .build());
  }
}
