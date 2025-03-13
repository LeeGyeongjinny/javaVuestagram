package com.example.vuestagram.service;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.dto.response.ResponseLogin;
import com.example.vuestagram.model.User;
import com.example.vuestagram.repository.UserRepository;
import com.example.vuestagram.util.CookieUtil;
import com.example.vuestagram.util.jwt.JwtUtil;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfiguration 에 정의되어있어서 바로 사용 가능
    private final CookieUtil cookieUtil;
    private final JwtConfig jwtConfig;

//    public String login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
    public ResponseLogin login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
//        User user = new User();
//        user.setUserId(2L);
//        Optional<User> result = userRepository.findById(9L); // 괄호 안에 PK 번호 넣음, 9L : 9번 Long

        Optional<User> result = userRepository.findByAccount(loginRequestDTO.getAccount());

        // 유저 존재 여부 체크
        if(result.isEmpty()) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }

        // 비밀번호 체크
        if(!(passwordEncoder.matches(loginRequestDTO.getPassword(), result.get().getPassword()))) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        // 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(result.get());
        String refreshToken = jwtUtil.generateRefreshToken(result.get());

        // refreshToken 쿠키에 저장
        cookieUtil.setCookie(
                response
                ,jwtConfig.getRefreshTokenCookieName()
                ,refreshToken
                ,jwtConfig.getRefreshTokenCookieExpiry()
                ,jwtConfig.getReissUri()
        );

//        return accessToken + " || " + refreshToken;
        return ResponseLogin.builder()
                .accessToken(accessToken)
                .userId(result.get().getUserId())
                .account(result.get().getAccount())
                .profile(result.get().getProfile())
                .name(result.get().getName())
                .build();
                // get()쓰는 이유 ? result 가 optional 객체라서
    }
}
