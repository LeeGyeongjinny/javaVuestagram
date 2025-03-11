package com.example.vuestagram.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {
    // Request Header 에서 특정 쿠키를 획득하는 메소드
    public Cookie getCookie(HttpServletRequest request, String name) {
        // try 문 이걸 간단하게 적으면 밑에 return 코드
//        try {
//            Cookie[] cookies = request.getCookies();
//            if(cookies != null) {
//                new RuntimeException("쿠키 없다.");
//            }
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }

        // 쿠키가 없는 경우도 있으므로
        // Optional.ofNullable 메소드를 사용해서 null 을 가질 수 있는 Optional 생성
        return Arrays.stream(Optional.ofNullable(request.getCookies())
                        // null 와도 stream 으로 변경할 때 에러나지 않게 Optional 생성
                        .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없다."))
                ) // Stream<Cookie[]> 생성
                .filter(item -> item.getName().equals(name)) // 필터 조건에 맞는 Stream 을 리턴(중간 연산자)
                .findFirst() // 필터 조건에 맞는 첫 번째 아이템을 선택해서 Optional로 리턴(최종 연산)
                .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없다."));
    }

    // Response Header 에 쿠키 설정 메소드
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        // return 타입 필요 없음
        Cookie cookie = new Cookie(name, value); // 이름, 값을 보내면 키 value 에 cookie 값을 가지는 객체 생성?
        cookie.setPath(domain); // 특정 요청으로 들어올 때만 쿠키를 넘겨주도록 설정, 받은 domain 세팅
        cookie.setMaxAge(maxAge); // 쿠키 만료시간 설정
        cookie.setHttpOnly(true); // 보안 쿠키 설정, 프론트에서 js 쿠키 획득이 불가능
        response.addCookie(cookie);
    }
}
