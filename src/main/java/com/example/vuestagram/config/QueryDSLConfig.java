package com.example.vuestagram.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfig {
    // @PersistenceContext :
    // JPA 에서 DB 와 상호작용 하기 위한 객체 (EntityManager) 를
    // Spring Context 에 자동으로 주입
    @PersistenceContext
    private EntityManager em; // EntityManager 는 인터페이스

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
