package com.example.vuestagram.repository;

import com.example.vuestagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // <User, Long> = <Entity 클래스, PK의 데이터타입>
    // 추상메소드 아무것도 안적었는데 왜 에러 안나? JpaRepository 를 상속 받는 중이라 추상메소드 있다고 인식 중

    Optional<User> findByAccount(String account);
}
