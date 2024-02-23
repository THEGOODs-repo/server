package com.umc.TheGoods.config.springSecurity;

import com.umc.TheGoods.config.springSecurity.handler.CustomAccessDeniedHandler;
import com.umc.TheGoods.config.springSecurity.handler.CustomAuthenticationEntryPoint;
import com.umc.TheGoods.config.springSecurity.handler.JwtAuthenticationExceptionHandler;
import com.umc.TheGoods.config.springSecurity.provider.TokenProvider;
import com.umc.TheGoods.config.springSecurity.utils.JwtFilter;
import com.umc.TheGoods.redis.service.RedisService;
import com.umc.TheGoods.service.MemberService.MemberCommandServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


//configure 함수를 오버라이드 해서 설정할 수 있습니다
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {



    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationExceptionHandler exceptionFilter;
    private final RedisService redisService;
    private final TokenProvider tokenProvider;
    @Value("${jwt.token.secret}")
    private String secretKey;

    private final UrlBasedCorsConfigurationSource corsConfigurationSource;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers(
                        "/favicon.ico",
                        "/health",
                        "/",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/api/members/login"
                );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .httpBasic().disable()//토큰 인증 방식으로 하기 위해서 HTTP 기본 인증 비활성화
                .csrf().disable()//CSRF 공격 방어 기능 비활성화
                .cors()
                .configurationSource(corsConfigurationSource)

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()//모든 접근 허용
                //.antMatchers(HttpMethod.POST, "/api/members/jwt/test").authenticated()//인증 필요로 접근 막기

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()


                .addFilterBefore(new JwtFilter(tokenProvider, redisService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionFilter, JwtFilter.class)
                .build();


    }
}
