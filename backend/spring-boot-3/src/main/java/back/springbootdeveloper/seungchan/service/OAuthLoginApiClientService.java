package back.springbootdeveloper.seungchan.service;

public interface OAuthLoginApiClientService<P> {
    P requestOAuthLogin(String authCode);
}
