package com.umc.TheGoods.config.springSecurity;

import com.umc.TheGoods.config.springSecurity.utils.JwtTokenFilter;
import com.umc.TheGoods.service.MemberService.MemberCommandServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//configure 함수를 오버라이드 해서 설정할 수 있습니다
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {


    private final MemberCommandServiceImpl memberCommandServiceImpl;
    @Value("${jwt.token.secret}")
    private String secretKey;

    //SpringSecurity 5.5이상부터는 SecurityFilterChain을 Bean으로 등록해서 사용
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .httpBasic().disable()//토큰 인증 방식으로 하기 위해서 HTTP 기본 인증 비활성화
                .csrf().disable()//CSRF 공격 방어 기능 비활성화
                .cors().and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()//모든 접근 허용
                //.antMatchers(HttpMethod.POST, "/api/members/jwt/test").authenticated()//인증 필요로 접근 막기
                .and()
                .sessionManagement()
                //세션 관리 정책 설정(jwt를 사용하면 사용자가 로그인 했을때 jwt 토큰을 넘겨줘서 토큰으로 사용자 인증을 하면 되어서 세션을 사용하지 않는다.)
                //세션으로 인증하는 방식은 사용자가 로그인하면 사용자의 세션을 부여해서 인증된 사용자 정보를 서버에서 기억하는 방식
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(memberCommandServiceImpl, secretKey), UsernamePasswordAuthenticationFilter.class)
                //UsernamePasswordAuthenticationFilter 앞에 JwtTokenFilter를 둔다. 그 이유는 이미 로그인을 했고 토큰을 발급 받아서 토큰 이용해서 인증하면 된다.
                .build();


    }
}
