package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.judgement.MEMBERSHIP_STATUS;
import back.springbootdeveloper.seungchan.dto.request.LoginFirstReqDto;
import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import back.springbootdeveloper.seungchan.dto.response.LoginResDto;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

  private final MemberService memberService;
  private final MemberRepository memberRepository;
  private final OAuthLoginApiClientService<GoogleOAuthProfile> oAuthLoginApiClientService;
  private final TokenService tokenService;
  private final RefreshTokenService refreshTokenService;

  public LoginResDto loginGoogle(LoginReqDto request) {
    String loginEmail = "";
    try {
      // Google Login 요청
      GoogleOAuthProfile profile = oAuthLoginApiClientService.requestOAuthLogin(
        request.getAuthCode());
      loginEmail = profile.getEmail();

      // Email 정보를 얻은 후 확인
      Member member = memberService.findByEmail(loginEmail);
      // Check refresh token exist
      String existedRefreshToken = refreshTokenService.findByMemberId(member.getMemberId())
        .getRefreshToken();
      boolean isValidRefreshToken = tokenService.isValidToken(existedRefreshToken);

      // FefreshToken 검증
      if (isValidRefreshToken) {
        // Create access token only
        return new LoginResDto(tokenService.createNewAccessToken(existedRefreshToken));
      } else {
        // Create Refresh and access Token
        return new LoginResDto(tokenService.createAccessAndRefreshToken(loginEmail));
      }
    } catch (Exception e) {
      return new LoginResDto(MEMBERSHIP_STATUS.NON_MEMBER);
    }
  }

  /**
   * 처음으로 구글을 통해 로그인하는 회원을 등록합니다.
   *
   * @param loginFirstReqDto 처음 로그인하는 회원의 정보를 담은 DTO 객체
   * @return 등록된 회원 정보
   */
  public Member firstLoginGoogle(LoginFirstReqDto loginFirstReqDto) {
    Member member = new Member(loginFirstReqDto);

    return memberRepository.save(member);
  }
}
