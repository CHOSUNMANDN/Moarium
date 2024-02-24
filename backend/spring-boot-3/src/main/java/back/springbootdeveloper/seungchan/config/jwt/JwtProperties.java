package back.springbootdeveloper.seungchan.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// applicatin.yml의
// # JWT 토큰을 만들려면 이슈 발급자(issuer), 비밀키(secret_key)를 필수적으로 설정
//  jwt:
//    issuer: ajufresh@gmail.com
//    secret_key: study-springboot
// 의 정보를 가져온다.
@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String issuer;

    @Value(value = "${jwt.secret-key}")
    private String secretKey;
}