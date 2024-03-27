package back.springbootdeveloper.seungchan.controller;


import back.springbootdeveloper.seungchan.dto.response.ClubFindInformation;
import back.springbootdeveloper.seungchan.dto.response.MainClubFindResDto;
import back.springbootdeveloper.seungchan.dto.response.SearchResult;
import back.springbootdeveloper.seungchan.dto.response.SearchResultResDto;
import back.springbootdeveloper.seungchan.service.ClubService;
import back.springbootdeveloper.seungchan.service.SearchService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main page의 Controller", description = "로그인 이후의 페이지를 담당한다.")
@RestController
@RequestMapping(value = "/main")
@RequiredArgsConstructor
public class MainController {

  private final TokenService tokenService;
  private final ClubService clubService;
  private final SearchService searchService;

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

  @Operation(summary = "메인 페이지 동아리 검색 조회", description = "메인 페이지의 등록 되어 있는 클럽들의 정보 조회")
  @GetMapping(value = "/search")
  public BaseResultDTO<SearchResultResDto> searchClubInformation(HttpServletRequest request,
      @RequestParam(value = "search") String search) {
    Long loginMemberId = tokenService.getMemberIdFromToken(request);
    List<SearchResult> searchResults = searchService.getSearchResults(search);

    return BaseResultDTO.ofSuccess(SearchResultResDto.builder()
        .searchResults(searchResults)
        .build());
  }
}
