package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.GoogleOAuthTokenReqDto;
import back.springbootdeveloper.seungchan.dto.request.GoogleOAuthLoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthLoginResDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthTokenInfoResDto;
import back.springbootdeveloper.seungchan.filter.handler.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@Service
public class GoogleOAuthLoginApiClientService implements OAuthLoginApiClientService<GoogleOAuthProfile> {
    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.login.url}")
    private String googleLoginUrl;

    @Value("${google.auth.scope}")
    private String scopes;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    private final GoogleOAuthTokenReqDto command;


    private void init(){
        restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
        objectMapper = new ObjectMapper();
    }

    public String getTokenURI() {
        return UriComponentsBuilder.fromHttpUrl(getGoogleAuthUrl()+"/token").toUriString();
    }

    public String getGoogleTokenInfoURI(String accessToken){
        return UriComponentsBuilder.fromHttpUrl(getGoogleAuthUrl()+"/tokeninfo").queryParam("access_token", accessToken).toUriString();
    }


    private String requestOAuthClientAccessToken(String authCode) throws JsonProcessingException {
        // init
        init();

        // Make Request
        GoogleOAuthLoginReqDto request= command.makeOAuthTokenReqBody(authCode);

        // Request /token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GoogleOAuthLoginReqDto> httpRequestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> apiResponseString = restTemplate.postForEntity(getTokenURI(), httpRequestEntity, String.class);
        GoogleOAuthLoginResDto googleOAuthLoginResDto = objectMapper.readValue(apiResponseString.getBody(), new TypeReference<GoogleOAuthLoginResDto>() {});

        return googleOAuthLoginResDto.getAccessToken();
    }

    private GoogleOAuthProfile requestOauthInfo(String accessToken) throws JsonProcessingException {
        String requestTokenInfoUrl = getGoogleTokenInfoURI(accessToken);
        String resultTokenInfo = restTemplate.getForObject(requestTokenInfoUrl,String.class);

        // Check member exist
        if(resultTokenInfo == null) {
            System.out.println("Does not exist");
        }
        GoogleOAuthTokenInfoResDto response = objectMapper.readValue(resultTokenInfo, new TypeReference<GoogleOAuthTokenInfoResDto>() {});
        return new GoogleOAuthProfile(response.getEmail());
    }

    @Override
    public GoogleOAuthProfile requestOAuthLogin(String authCode) {
        try {
            String accessToken = requestOAuthClientAccessToken(authCode);
            return requestOauthInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
