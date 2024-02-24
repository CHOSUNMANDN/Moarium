//package back.springbootdeveloper.seungchan.controller.config.jwt;
//
//import back.springbootdeveloper.seungchan.config.jwt.JwtProperties;
//import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
//import back.springbootdeveloper.seungchan.entity.UserInfo;
//import back.springbootdeveloper.seungchan.repository.UserRepository;
//import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
//import io.jsonwebtoken.Jwts;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Duration;
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class TokenProviderTest {
//
//    @Autowired
//    private TokenProvider tokenProvider;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtProperties jwtProperties;
//
//    @Autowired
//    private UserUtilRepository userUtilRepository;
//
//    // 토큰을 생성하는 메서드를 테스트하는 메서드
//    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
//    @Test
//    void generateToken() {
//        // given
//        // 토큰에 유저 정보를 추가하기 위한 테스트 유저를 만든다.
//        // 토큰 제공자의 generateToken(); 메서드를 호출해 토큰을 만든다.
//        userRepository.deleteAll();
//        userUtilRepository.deleteAll();
//
//        Object TestMakeObject;
//        UserInfo user = TestMakeObject.makeUser("박승찬", "seungchan141414@gmail.com");
//        UserInfo testUser = userRepository.save(user);
//
//        userUtilRepository.save(TestMakeObject.makeUserUtill(user, 0, true));
//
//        // when
//        // 토큰 제공자의 generateToken()메서드를 호출해 토큰을 만든다.
//        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
//
//        // then
//        // jjwt 라이브러리를 사용해 토큰을 복호화한다.
//        // 토큰을 만들 때 클레임으로 넣어둔 id값이 given절에서 만든 유저 ID와 동일한지 확인
//        Long userId = Jwts.parser()
//                .setSigningKey(jwtProperties.getSecretKey())
//                .parseClaimsJws(token)
//                .getBody()
//                .get("id", Long.class);
//
//        boolean nuriKing = Jwts.parser()
//                .setSigningKey(jwtProperties.getSecretKey())
//                .parseClaimsJws(token)
//                .getBody()
//                .get("isNuriKing", Boolean.class);
//
//        assertThat(userId).isEqualTo(testUser.getId());
//        assertThat(nuriKing).isTrue();
//    }
//
//    @DisplayName("validToken(): 만료된 토큰인 경우에 유효성 검증에 실패한다.")
//    @Test
//    void validToken_invalidToken() {
//        // given
//        // jjwt 라이브러리를 사용해 토큰을 생성한다.
//        // 이때 만료 시간은 1970년 1월 1일부터 현재 시간을 밀리초 단위로 치환한 값
//        // 에서 1000을 빼 이미 만료된 토큰을 생성한다.
//        JwtFactory jwtFactory = new JwtFactory(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()));
//        String token = jwtFactory.createToken(jwtProperties);
//        // when
//        // 토큰 제공자의 validToken()메서드를 호출해 유효한 토큰인지 검증한 뒤 결과를 반환
//        boolean result = tokenProvider.validToken(token);
//
//        // then
//        // 반환값이 false(유효한 토큰이 아님)인 것을 확인
//        assertThat(result).isFalse();
//    }
//}
