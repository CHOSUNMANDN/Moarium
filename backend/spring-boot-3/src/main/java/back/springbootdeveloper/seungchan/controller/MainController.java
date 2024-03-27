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

  @Operation(summary = "메인 페이지 즐겨찾기 정보 조회", description = "메인 페이지의 즐겨 찾기 목록의 클럽 정보들의 조회")
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
