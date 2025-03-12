package com.example.vuestagram.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor // relationship 사용할 수 있도록
@Entity // Entity 클래스
@EnableJpaAuditing // created_at, updated_at 자동 관리
@EntityListeners(AuditingEntityListener.class) // watch 처럼 변화 감시
@Table(name = " users")
@SQLDelete(sql = "UPDATE users SET update_at = NOW(), deleted_at = NOW() WHERE user_id = ?") // deleted 메소드 실행할 때 그거 대신에 실행할 softDelete(update) 쿼리 적어주면 됨
@Where(clause = "deleted_at IS NULL") // select 할 때 softDelete 된 것은 제외하고 가져오도록 clause 에 정의할 수 있음
public class User {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 처럼 1씩 증가하면서 관리
    @Column(name = "user_id") // 맵핑할(연결할) column 이나 제약조건 설정 // but 모든 제약조건을 여기서 설정하지는 않는다, not null, 크기, unique 설정정도는 여기서 가능
    private Long userId; // MariaDB 에서 BigInt -> Long, Int -> Int

    @Column(name = "account", unique = true, nullable = false, length = 20) // 데이터타입은? 프로퍼티 설정할 때 String 으로 줬음
    private String account;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "profile", length = 100) // nullable 디폴트는 true
    private String profile;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정일어날 때 자동으로 갱신
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at") // deleted_at 은 class annotation 에서 이미 설정함
    private LocalDateTime deletedAt;
}
