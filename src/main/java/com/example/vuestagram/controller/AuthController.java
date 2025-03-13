package com.example.vuestagram.controller;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.dto.response.ResponseBase;
import com.example.vuestagram.dto.response.ResponseLogin;
import com.example.vuestagram.service.AuthService;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST API 컨트롤러
//@RequestMapping("/api")
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
//    public String login(
    public ResponseEntity<ResponseBase<ResponseLogin>> login(
            HttpServletResponse response
            ,@Valid @RequestBody LoginRequestDTO loginRequestDTO
            ) {

//        return loginRequestDTO.getAccount() + ":" + loginRequestDTO.getPassword();
//         return authService.login(loginRequestDTO, response);
        ResponseLogin responseLogin = authService.login(loginRequestDTO, response);

        ResponseBase<ResponseLogin> responseBase = ResponseBase.<ResponseLogin>builder()
                                                                .status(200)
                                                                .message("로그인 성공")
                                                                .data(responseLogin)
                                                                .build();
        return ResponseEntity.status(200).body(responseBase);
        // ResponseEntity 안에 responseBase 가 담겨서 제너릭스로 ResponseBase 들어감
        // ResponseBase 가 responseLogin 을 가지기 때문에 제너릭스로 ResponseLogin 들어감
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
