package com.example.vuestagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = " boards")
@SQLDelete(sql = "UPDATE boards SET update_at = NOW(), deleted_at = NOW() WHERE board_id = ?") // deleted 메소드 실행할 때 그거 대신에 실행할 softDelete(update) 쿼리 적어주면 됨
@Where(clause = "deleted_at IS NULL") // select 할 때 softDelete 된 것은 제외하고 가져오도록 clause 에 정의할 수 있음
public class Board {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

//    @ManyToOne(fetch = FetchType.LAZY) // board : many, user : one, fetch 디폴트 Eager
    @ManyToOne
    @JoinColumn(name = "user_id") // fk 를 부여할 board 의 컬럼명, fk 연결하면 자동으로 user_id 찾아서 나머지 세팅함
    private User user; // 연결할 객체를 지정

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "img", nullable = false, length = 200)
    private String img;

    @Column(name = "likes", nullable = false, length = 11)
    private int likes;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
