package com.helpduk.helpDuk.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 어플리케이션의 보안 설정
 * 예제 13.19
 *
 * @author Flature
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity // Spring Security에 대한 디버깅 모드를 사용하기 위한 어노테이션 (default : false)
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(withDefaults()) // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
                .csrf(AbstractHttpConfigurer::disable)// REST API는 csrf 보안이 필요 없으므로 비활성화
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/v2/api-docs", "/swagger-resources/**",
                                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/api/sign/exception").permitAll()
                        .requestMatchers("/api/sign/sign-in", "/api/sign/sign-up",
                                "/api/sign/exception").permitAll() // 가입 및 로그인 주소는 허용
                        .requestMatchers("**exception**").permitAll()
                        .anyRequest().permitAll()
//                        .anyRequest().authenticated() // 나머지 요청은 인증된 ADMIN만 접근 가능
                )
                .exceptionHandling((authenti) ->
                        authenti.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .exceptionHandling((authenti) ->
                        authenti.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가

        return httpSecurity.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer()  {
//        return (web) -> web.ignoring().requestMatchers("/v2/api-docs", "/swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
//    }
}