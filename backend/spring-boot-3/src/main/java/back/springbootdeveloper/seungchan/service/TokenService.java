package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
import back.springbootdeveloper.seungchan.entity.RefreshToken;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.RefreshTokenRepository;
import back.springbootdeveloper.seungchan.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token"; // 리프레쉬 토큰의 이름
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token"; // 리프레쉬 토큰의 이름
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14); // 리프레쉬 토큰의 유효기간
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1); // 액세스 토큰의 유효기간

    private final static String HEADER_AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        UserInfo user = userService.findUserById(userId);

        return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
    }

    public String createAccessAndRefreshToken(HttpServletRequest request, HttpServletResponse response, String userEmail) {
        // 이메일을 기반으로 사용자 정보를 조회
        UserInfo user = userService.findByEmail(userEmail);

        // 새로운 리프레쉬 토큰을 생성한다.
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

        // 리프레쉬 토큰을 db에 저장한다.
        saveRefreshToken(user.getUserInfoId(), refreshToken);

        // 리프레쉬 토큰을 쿠키에 추가한다.
        addRefreshTokenToCookie(request, response, refreshToken);

        // 새로운 access 토큰을 생성한다.
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);


        return accessToken;
    }


    // 사용자 ID를 기반으로 refresh 토큰을 저장하거나 갱신한다.
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    // refresh 토큰을 쿠키에 추가한다.
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    public Long getUserIdFromToken(HttpServletRequest request) {
        String token = getToken(request);
        return tokenProvider.getUserId(token);
    }

    public Boolean getNuriKingFromToken(HttpServletRequest request) {
        String token = getToken(request);
        return tokenProvider.getIsNuriKing(token);
    }

    private String getToken(HttpServletRequest request) {
        // HTTP Request에서 "Authorization" 헤더 값 얻기
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // 토큰이 없는 경우나 "Bearer " 접두사를 포함하지 않은 경우 처리
        if (header == null || !header.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid token");
        }

        // "Bearer " 접두사를 제거하여 실제 토큰 얻기
        String token = header.replace("Bearer ", "");

        return token;
    }
}
