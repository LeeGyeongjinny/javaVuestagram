package com.example.vuestagram.repository;

import com.example.vuestagram.model.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"user"}) // board 에 있는 user 프로퍼티
    Optional<Board> findById(long id);
}
