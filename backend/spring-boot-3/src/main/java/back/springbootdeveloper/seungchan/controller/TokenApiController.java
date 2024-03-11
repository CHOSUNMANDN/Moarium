package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.CreateAccessTokenRequest;
import back.springbootdeveloper.seungchan.dto.response.CreateAccessTokenResponse;
import back.springbootdeveloper.seungchan.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "새로운 Access token 발급 API", description = "refresh token을 이용한 새로운 Access token 발급을 할수있다.")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TokenApiController {

  private final TokenService tokenService;

  // 재 발급을 위한 토큰 발급
  @PostMapping("/api/token")
  public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
      @RequestBody CreateAccessTokenRequest request) {
    String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateAccessTokenResponse(newAccessToken));
  }
}
