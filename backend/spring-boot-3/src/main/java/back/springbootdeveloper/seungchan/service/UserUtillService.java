package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final이 붙거나  @NotNull이 붙는 필드의 생성자 추가
@ToString
public class UserUtillService {
    private final UserUtilRepository userUtilRepository;

    public UserUtill findUserByUserId(Long userId) {
        return userUtilRepository.findByUserId(userId);
    }
}
