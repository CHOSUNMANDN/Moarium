package back.springbootdeveloper.seungchan.config.jwt;

import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.service.MemberService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import io.jsonwebtoken.Claims;

@RequiredArgsConstructor
@Component
public class TokenProvider {
    // 토큰을 생성하고 올바른 토큰인지 유효성 검사를 하고
    // 토큰에서 필요한 정보를 가져오는 클래스를 작성

    // application-oauth에 등록한 키값 가져온다.
    private final JwtProperties jwtProperties;
    private final MemberService memberService;

    // 토큰 생성을 한다. user의 정보와 원하는 유효기간을 매개 변수로 받는다.

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        // 현제 시간 + 원하는 유효기간을 토대로 토큰을 만든다.
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()),member);
    }

    // 1. JWT 토큰 객체 생성 메서드
    // 토큰을 생성하는 메서드. 인자는 만료 시간, 유저 정보를 받는다.
    // 이 메서드에서는 set계열의 메서드를 통해 여러 값을 지정
    // 헤더는 typ(타입),
    // 내용 iss(발급자), iat(발급일시), exp(만료일시), sub(토큰 제목)이, 클레임에는 유저 ID를 지정
    // 토큰을 만들 때는 프로퍼티즈 파일에 선언해둔 비밀값과 함께 HS256 방식으로 암호화한다.
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        // 헤더 : 타입, alg | 내용 : 키, 값 | 서명
        return Jwts.builder()
                // JWT 헤더
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                // JWT 내용
                .setIssuer(jwtProperties.getIssuer()) // 내용 iss : ajufresh@gmail.com
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫 값 / 토큰 만료기간 / 현제 + 만료 기간

                .setSubject(member.getEmail()) // 내용 sub : 유저의 이메일
                .claim("MemberId", member.getMemberId()) // 클레임 id : 유저 ID
//                .claim("isMoariumKing",) // TODO: Moarium 전체 관리자에 대한 상의가 필요
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 2. JWT 토큰 유효성 검증 메서드
    // 토큰이 유효한지 검증하는 메서드
    // 프로퍼티즈 파일에 선언한 비밀값과 함께 토큰 복호화를 진행합니다.
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    // 검증에 사용할 서명 키(Signing Key)를 설정합니다.
                    // jwtProperties.getSecretKey()는 서명 키를 가져오는 메서드로 가정됩니다.
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값 으로 복호화
                    // 주어진 토큰을 파싱하고 클레임을 검증합니다.
                    // token은 파싱할 JWT 토큰을 가리킵니다.
                    .parseClaimsJws(token); // 검증한다.
            return true;
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    // 토큰 기반으로 사용자 ID를 가져오는 메서드
    // 프로퍼티즈 파일에 저장한 비밀값으로 토큰을 복호화한 다음
    //  클레임을 자져오는 private 메서드인 getClaims()를 호출하여
    // 클레임 정보를 반환받고 클레임에서 id 킬로 저장된 값을 가져와 반환합니다.
    public Long getUserId(String token) {
        Claims claims = getClaims(token); // 토큰의 정보를 가져오는 claims 만들기
        return claims.get("MemberId", Long.class); // 토큰 기반으로 유저 id 가져오기
    }

    // TODO: 8/8/23  MoariumKing을 가져온다.
    public Boolean getIsNuriKing(String token) {
        Claims claims = getClaims(token); // 토큰의 정보를 가져오는 claims 만들기
        return claims.get("isMoariumKing", Boolean.class); // 토큰 기반으로 유저 id 가져오기
    }

    // 토큰 기반의 claims 가져오기
    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회, "JwtParser"를 생성한다. 이 객체는 JWT를 파싱하고 검증하는 역할
                .setSigningKey(jwtProperties.getSecretKey()) // 검증에 사용할 서명 키를 설정
                .parseClaimsJws(token) // 주어진 토큰을 파싱하여 검증한다. "token"은 파싱할 JWT 토큰을 가리킨다.
                .getBody(); // 검증된 토큰에서 클레임(Claims) 부분을 가져온다. 클레임 반환
    }

    // 3. 토큰 기반으로 인증 정보를 가져오는 메서드
    // 토큰을 받아 인증 정보를 담은 객체 Authentication를 반환하는 메서드입니다.
    // 프로퍼티즈 파일에 저장한 비밀 값으로 토큰을 복호화한 뒤
    // 클레임을 가져오는 private 메서드인 getClaims()를 호출해서
    // 클레임 정보를 반환받아 사용자 이메일이 들어 있는 토큰 제목 sub와 토큰 기반으로 인증 정보를 생성한다.
    // 이때 UsernamePasswordAuthenticationToken 의 첫 인자로 들어가는
    //   User는 프로젝트에서 만든 User 클래스가 아닌, 스프링 시큐리티에서 제공하는 객체인 User 클래스를 임포트해야 합니다.
    // 토큰 -> claims -> 정보 조회
    public Authentication getAuthentication(String token) {
        // 토큰 기반으로 claimes 가져오기, 토큰의 정보가 있다.
        // 주어진 토큰을 파싱하여 토큰의 정보를 담은 "Claims" 객체를 가져온다.
        Claims claims = getClaims(token);

        // SimpleGrantedAuthority 객체는 스프링 시큐리티에서 사용되는 인증 권한(authority)을 나타 내는 클래스
        // 사용자의 권한을 나타낸다
        // "ROLE_USER"나 "ROLE_ADMIN"과 같은 권한을 가질 수 있습니다.
        // 인증된 사용자에게 특정 리소스에 대한 접근 권한을 부여하는 데 사용될 수 있습니다.
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities):
        // 주어진 주제(subject)를 사용하여 User 객체를 생성합니다.
        // UsernamePasswordAuthenticationToken : 이 객체는 인증된 사용자의 정보와 권한을 담고 있으며, 스프링 시큐리티에서 인증과 인가에 사용됩니다.
        return new UsernamePasswordAuthenticationToken
                (new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities)
                        , token, authorities);
    }
}