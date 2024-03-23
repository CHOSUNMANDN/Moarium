package back.springbootdeveloper.seungchan.dto.request;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:application-oauth.properties")
public class GoogleOAuthTokenReqDto implements OAuthTokenReqDto<GoogleOAuthLoginReqDto> {

  @Value("${google.redirect.uri}")
  private String googleRedirectUrl;

  @Value("${google.client.id}")
  private String googleClientId;

  @Value("${google.secret}")
  private String googleSecret;

  @Override
  public GoogleOAuthLoginReqDto makeOAuthTokenReqBody(String authCode) {
    return GoogleOAuthLoginReqDto.builder()
        .clientId(getGoogleClientId())
        .clientSecret(getGoogleSecret())
        .code(authCode)
        .redirectUri(googleRedirectUrl)
        .grantType("authorization_code")
        .build();
  }

}
