package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.dto.response.UserLoginResponse;
import back.springbootdeveloper.seungchan.service.LoginService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인, 신입 가입 신청 관련 API", description = "로그인, 신입의 가입 신청 관리한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class LoginPageController {
    private final LoginService loginService;
    private final TokenService tokenService;

    @Operation(summary = "로그인", description = "기존 회원들의 로그인이다")
    @ResponseBody
    @PostMapping("/login")
    public BaseResultDTO<UserLoginResponse> userLogin(@RequestBody @Valid UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserLoginResponse userLoginResponse = loginService.login(userLoginRequest, request, response);

        return BaseResultDTO.ofSuccess(userLoginResponse);
    }
}
