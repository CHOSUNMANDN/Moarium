package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.LoginFirstReqDto;
import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.ImageResDto;
import back.springbootdeveloper.seungchan.dto.response.LoginResDto;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.service.LoginService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인, 신입 가입 신청 관련 API", description = "로그인, 신입의 가입 신청 관리한다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/login")
@CrossOrigin(value = "*")
@ResponseBody
public class OAuthLoginController {

  private final LoginService loginService;
  private final TokenService tokenService;

  @Operation(summary = "로그인", description = "Google OAuth 로그인이다.")
  @ResponseBody
  @PostMapping("/google")
  public BaseResultDTO<LoginResDto> googleLogin(@RequestBody @Valid LoginReqDto request) {
    // Get Access Token
    LoginResDto loginResDto = loginService.loginGoogle(request);

    // TODO: Response Format 맞추기
    return BaseResultDTO.ofSuccessWithMessage(ResponseMessage.OAUTH_LOGIN_SUCCESS.get(),
        loginResDto);
  }

  @Operation(summary = "초기 로그인", description = "초기 회원의 Google OAuth 로그인이다.")
  @ResponseBody
  @PostMapping("/google/first")
  public BaseResultDTO<LoginResDto> firstGoogleLogin(@RequestBody @Valid LoginFirstReqDto request) {
    // Get Access Token
    Member member = loginService.firstLoginGoogle(request);

    // Response Format 맞추기
    return BaseResultDTO.ofSuccess(
        new LoginResDto(tokenService.createAccessAndRefreshToken(member.getEmail())));
  }
}
