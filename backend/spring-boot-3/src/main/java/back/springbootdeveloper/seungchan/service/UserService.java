package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.filter.exception.judgment.UpdateFailedException;
import back.springbootdeveloper.seungchan.filter.exception.user.UserNotExistException;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
public class UserService {
    private final UserRepository userRepository;

    public UserInfo findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotExistException::new); // 찾아서 없으면 예외처리.;
    }

    public UserInfo findByEmail(String email) { // 유저 email 정보로 유저 객체를 찾기
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}

