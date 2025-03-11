package com.example.vuestagram.security;

import com.example.vuestagram.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil; // 생성자에서만 초기화 가능
    private final SecurityAuthenticationProvider securityAuthenticationProvider;

    @Override
    // 엑세스 토큰의 유효 여부를 확인하고 인증 정보를 스프링 시큐리티에 설정하는 메소드
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에서 토큰 획득
        String token = jwtUtil.getAccessTokenInCookie(request);

        if (token != null) {
            // 토큰을 스프링 시큐리티에 저장
            try {
                // Security 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(securityAuthenticationProvider.authenticate(token));
            } catch(Exception e) {
                throw new RuntimeException("사용할 수 없는 토큰");
            }
        }

        filterChain.doFilter(request, response); // 다음 필터 호출
    }
}
