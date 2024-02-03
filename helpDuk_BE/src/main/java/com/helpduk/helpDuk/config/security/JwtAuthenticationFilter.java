package com.helpduk.helpDuk.config.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

// 예제 13.17
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    private RedisTemplate<Object, Object> redisTemplate;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Override
//    public void doFilterInternal(HttpServletRequest servletRequest,
//        HttpServletResponse servletResponse,
//        FilterChain filterChain) throws IOException, ServletException {
//
//        // 헤더에서 JWT 를 받아옵니다.
//        String token = jwtTokenProvider.resolveToken(servletRequest);
//
//        // log를 이용한 확인
//        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
//
//        // 유효한 토큰인지 확인합니다. -> validation 진행
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            // Redis에 해당 accessToken logout 여부를 확인
//            String isLogout = (String) redisTemplate.opsForValue().get(token);
//
//            // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
//            if (ObjectUtils.isEmpty(isLogout)) {
//
//                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                // SecurityContext 에 Authentication 객체를 저장합니다.
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            }
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
        HttpServletResponse servletResponse,
        FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 JWT 받아오기
        String token = jwtTokenProvider.resolveToken(servletRequest);
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        // 유효한 토큰인지 확인 -> validation 진행
        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}