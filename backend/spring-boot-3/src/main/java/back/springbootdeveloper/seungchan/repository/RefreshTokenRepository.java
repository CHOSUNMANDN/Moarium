package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByMemberId(Long memberId);

  Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
