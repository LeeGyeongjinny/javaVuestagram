package com.example.vuestagram.service;

import com.example.vuestagram.model.Board;
import com.example.vuestagram.model.QBoard;
import com.example.vuestagram.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory; // QueryDSLConfig 에서 @Bean 하고 설정해줘서 쓸 수 있음

    public Board test() {
        // Optional<Board> board = boardRepository.findById(60L); // 60L : pk 60번

        // QueryDSL
        QBoard qBoard = QBoard.board; // QueryDSL 이 자동으로 생성해주는 Board Entity 기반의 클래스

        JPAQuery<Board> query = queryFactory.selectFrom(qBoard) // 우리가 검색할 q-class 넣어주면됨
                                    .where(
                                            qBoard.boardId.eq(60L) // eq : equals
                                    );
        // q-class 넣으면 q-class 에 정의되어있는 프로퍼티가 나옴

//        if(true) {
//            query.where(
//                    qBoard.boardId.eq(60L)
//            );
//        }

//        return board.get();
        return query.fetchFirst();
        // fetch : 여러 개를 리스트로 가져오고 싶을 때
        // fetchFirst : 하나만 가져오고 싶을 때
        // fecthAll : 거의 안씀
    }
}
