package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ClubFindInformation;
import back.springbootdeveloper.seungchan.dto.response.MainClubFindResDto;
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

@Tag(name = "클럽 리스트 페이지의 API 요청 컨트롤러", description = "클럽 리스트 페이지의 전체적인 페이지의 API의 요청을 담당한다.")
@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubListController {

  private final TokenService tokenService;
  private final ClubService clubService;

  @Operation(summary = "등록한 모든 동아리의 정보를 보여준다.", description = "메인 페이지의 즐겨 찾기 목록의 클럽 정보들의 조회")
  @GetMapping(value = "/informations")
  public BaseResultDTO<MainClubFindResDto> getMemberDetailsPage(HttpServletRequest request) {
    Long loginMemberId = tokenService.getMemberIdFromToken(request);

    List<ClubFindInformation> joinClubFindInformations = clubService.getJoinClubFindInformations(
        loginMemberId);

    return BaseResultDTO.ofSuccess(MainClubFindResDto.builder()
        .clubFindInformations(joinClubFindInformations)
        .build());
  }
}
