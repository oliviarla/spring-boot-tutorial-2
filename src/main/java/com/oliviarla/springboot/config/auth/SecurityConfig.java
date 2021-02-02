package com.oliviarla.springboot.config.auth;

import com.oliviarla.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() //전체 열람 권한 부여
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //USER 권한 가진 사람만 접근
                .anyRequest().authenticated() //나머지 URL은 인증된(로그인한) 사용자들에게만 허용
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2로그인 성공 후 사용자 정보 가져올 때의 설정 담당
                .userService(customOAuth2UserService); //로그인 성공 시 후속 조치 진행할 인터페이스의 구현체 등록
    }
}
