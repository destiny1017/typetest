package com.typetest.login;

import com.typetest.login.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final OAuthLoginService oAuthLoginService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션 disable
            .and()
            .authorizeRequests()// URL별 권한 권리
            .antMatchers("/hello").authenticated()
//            .antMatchers("/","/css/**","/images/**","/js/**").permitAll()
//            .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // /api/v1/** 은 USER권한만 접근 가능
            .anyRequest().permitAll() // anyRequest : 설정된 값들 이외 나머지 URL 나타냄, authenticated : 인증된 사용자
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
            // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
            .userService(oAuthLoginService); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
        return http.build();
    }
}
