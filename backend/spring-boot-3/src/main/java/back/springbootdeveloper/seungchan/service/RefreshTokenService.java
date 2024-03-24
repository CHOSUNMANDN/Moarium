package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.RefreshToken;
import back.springbootdeveloper.seungchan.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshToken findByRefreshToken(String refreshToken) {
    return refreshTokenRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
  }

  // TODO : Error Handling
  public RefreshToken findByMemberId(Long memberId) {
    return refreshTokenRepository.findByMemberId(memberId)
        .orElseThrow(() -> new IllegalArgumentException("Token does not exist"));
  }
}
