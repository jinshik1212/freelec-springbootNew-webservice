package com.jskim.book.springboot.config.auth;


import com.jskim.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable() // H2-console 화면 사용하기 위해 해당 옵션 disable
                .and()
                    .authorizeRequests()// URL 별 권한관리 설정 옵션 시작점
                    .antMatchers("/", "/css/**", "/images/**", // 권한관리 대상 지정
                            "/js/**", "/h2-console/**").permitAll() // 전체 열람
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // User 권한만 가능
                    .anyRequest().authenticated() // 설정된 값 이외 나머지 URL 은 인증된 사용자만 가능(로그인한)
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }

}
