package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.dto.response.UserLoginResponse;
import back.springbootdeveloper.seungchan.filter.exception.user.UserNotExistException;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class LoginService {
    private final UserRepository userRepository;
    private final UserUtilRepository userUtilRepository;
    private final TokenService tokenService;

    public UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        Optional<UserInfo> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotExistException();
        }

        UserUtill userUtill = userUtilRepository.findByUserId(user.get().getUserInfoId());

        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (new BCryptPasswordEncoder().matches(password, user.get().getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            String accessToken = tokenService.createAccessAndRefreshToken(request, response, user.get().getEmail());
            return new UserLoginResponse(accessToken, user.get(), userUtill);
        }

        throw new UserNotExistException();
    }
}

