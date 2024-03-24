package back.springbootdeveloper.seungchan.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 액세스토큰이 유효, 인증정보 설정
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final static String HEADER_AUTHORIZATION = "Authorization";
  private final static String TOKEN_PREFIX = "Bearer ";
  private final TokenProvider tokenProvider;

  @Override
  // HttpServletRequest : 클라이언트의 HTTP 요청에 대한 정보를 가지고 있는 객체입니다.
  //                      요청의 헤더, 바디, 파라미터, 세션 등의 정보에 접근할 수 있습니다.
  // HttpServletResponse : 서버에서 클라이언트로 보내는 HTTP 응답에 대한 정보를 가지고 있는 객체입니다.
  //                      응답의 상태 코드, 헤더, 바디 등을 설정할 수 있습니다.
  // FilterChain: 다음 필터로 요청을 전달하거나 필터 체인의 마지막 필터인 경우 실제 서블릿 또는 컨트롤러로 요청을 전달하는
  //              역할을 하는 객체입니다.

  // 요청 헤더의 TOKEN_PREFIX을 제거를 한 토큰의 값의 문자열을 가져온다.
  // 만약 값이 null이거나 Bearer로 시작하지 않으면 null을 반환합니다.
  // 가져온 토큰이 유효한지 확인하고, 유효하다면 인증 정보를 관리하는 시큐리티 컨텍스트에 인증 정보를 설정합니다.
  // 위에서 작성한 코드가 실행되며 인증 정보가 설정된 이후에 컨텍스트 홀더에서 getAuthentication() 메서드를 사용해
  // 인증 정보를 가져오면 유저 객체가 반환됩니다.
  // 객체에는 유저 이름(username)과 권한 목록(authorities)과 같은 인증 정보가 포함됩니다.
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // 요청 헤더의 Authorization 키의 값 조회
    String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

    // 가져온 Header의 값에서 접두사 제거
    String token = getAccessToken(authorizationHeader);

    // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보를 설정
    if (tokenProvider.validToken(token)) {
      // 인증 정보를 설정한다.
      Authentication authentication = tokenProvider.getAuthentication(token);

      // 스프링 시큐리티에서 현재 사용자의 보안 컨텍스트를 관리하는 클래스입니다.
      // SecurityContextHolder는 SecurityContext 객체를 저장하고 제공하는 역할을 수행합니다.
      // getContext(): 현재의 보안 컨텍스트를 반환합니다.
      // setAuthentication(authentication): 주어진 authentication 객체를 현재의 보안 컨텍스트에 설정합니다.
      //                                    이를 통해 인증 정보가 보안 컨텍스트에 연결되고,
      //                                    이후의 보안 관련 작업에서이 인증 정보를 활용할 수 있게 됩니다.
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//        FilterChain: 필터 체인을 구성하는 필터들 사이에서 요청과 응답을 전달하는 역할을 수행하는 인터페이스입니다.
//        doFilter(request, response): 현재 필터의 작업을 완료하고, 다음 필터로 요청을 전달합니다.
//                                     이 메서드를 호출함으로써 필터 체인의 다음 단계로 이동할 수 있습니다.
    filterChain.doFilter(request, response);
  }


  private String getAccessToken(String authorizationHeader) {
    // authorizationHeader: HTTP 요청 헤더에서 Authorization 헤더의 값을 나타내는 변수입니다.
    //                   일반적으로 Bearer 토큰 방식을 사용하여 인증 정보를 전달할 때 사용됩니다.
    // TOKEN_PREFIX: 액세스 토큰의 접두사를 나타내는 상수 변수입니다.
    //              일반적으로 Bearer 토큰 방식에서는 "Bearer "로 시작하는 문자열을 사용합니다.
    if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
      return authorizationHeader.substring(TOKEN_PREFIX.length());
    }
    return null;
  }
}

