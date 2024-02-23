package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.LoginService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인, 신입 가입 신청 관련 API", description = "로그인, 신입의 가입 신청 관리한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users/login")
@ResponseBody
public class OAuthLoginController {
    private final LoginService loginService;

    @Operation(summary = "로그인", description = "Google OAuth 로그인이다.")
    @ResponseBody
    @PostMapping("/google")
    public ResponseEntity<BaseResponseBody> googleLogin(@RequestBody @Valid LoginReqDto request) {
        // Get Access Token
        String accessToken = loginService.loginGoogle(request);

        // TODO: Response Format 맞추기
        return BaseResponseBodyUtiil.BaseResponseBodySuccess(accessToken);
    }
}
