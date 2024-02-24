package back.springbootdeveloper.seungchan.dto.request;

public interface OAuthTokenReqDto<T> {

    T makeOAuthTokenReqBody(String authCode);
}
