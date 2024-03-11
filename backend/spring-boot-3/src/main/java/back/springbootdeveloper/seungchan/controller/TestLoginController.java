package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.TestLoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.LoginResDto;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Test을 위한 로그인 컨트롤로", description = "Test을 위한 로그인 컨트롤로")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class TestLoginController {

  private final TokenService tokenService;

  @Operation(summary = "테스트을 위한 로컬 로그인", description = "테스트를 위한 로컬 로그인")
  @ResponseBody
  @PostMapping("/test/login")
  public BaseResultDTO<LoginResDto> testLogin(@RequestBody @Valid TestLoginReqDto request) {
    // Get Access Token
    LoginResDto loginResDto = new LoginResDto(
        tokenService.testCreateAccessAndRefreshToken(request.getMemberId()));

    // TODO: Response Format 맞추기
    return BaseResultDTO.ofSuccessWithMessage(ResponseMessage.OAUTH_LOGIN_SUCCESS.get(),
        loginResDto);
  }
}
