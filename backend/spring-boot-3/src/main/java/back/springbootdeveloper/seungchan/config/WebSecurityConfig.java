package back.springbootdeveloper.seungchan.config;

import back.springbootdeveloper.seungchan.config.jwt.TokenAuthenticationFilter;
import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
import back.springbootdeveloper.seungchan.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailService userService;
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()
                )
                // TODO: 8/28/23 H2을 사용하기위해 임시적으로 사용 실제 배포시 지우기
                .headers().frameOptions().disable();
        return http.build();
    }

}