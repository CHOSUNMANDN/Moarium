package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.entity.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberService memberService;
    private final OAuthLoginApiClientService<GoogleOAuthProfile> oAuthLoginApiClientService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public String loginGoogle(LoginReqDto request){
        try {
            // Google Login 요청
            GoogleOAuthProfile profile = oAuthLoginApiClientService.requestOAuthLogin(request.getAuthCode());
            String email = profile.getEmail();

            // Email 정보를 얻은 후 확인
            Member member = memberService.findByEmailForJwtToken(email);
            if(member == null){
                // Create new Member
                memberService.createMemberByEmail(email);
                return tokenService.createAccessAndRefreshToken(email);

            }else{
                // Check refresh token exist
                String existedRefreshToken = refreshTokenService.findByMemberId(member.getMemberId()).getRefreshToken();
                boolean isValidRefreshToken = tokenService.isValidToken(existedRefreshToken);
                if(isValidRefreshToken){
                    // Create access token only
                   return tokenService.createNewAccessToken(existedRefreshToken);
                }else{
                    // Create Refresh and access Token
                    return tokenService.createAccessAndRefreshToken(email);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
