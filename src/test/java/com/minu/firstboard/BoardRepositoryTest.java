package com.minu.firstboard;

import com.minu.firstboard.entity.Board;
import com.minu.firstboard.repository.BoardRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Order(4)
    @DisplayName("게시판 삭제 테스트")
    void deleteBoard() {
        boardRepository.deleteById(1L);
        Board board = boardRepository.findById(1L).orElse(null);

        assertNull(board);
    }


    @Test
    @Order(3)
    @DisplayName("게시판 수정 테스트")
    void updateBoardTest() {
        Board board = boardRepository.findById(1L).orElse(null);
        assertTrue(board != null);

        board.setTitle("Update Title");
        board.setContent("Update Content");
        boardRepository.save(board);

        // 못찾으면 새로운 board객체 반환
        Board board2 = boardRepository.findById(1L).orElse(new Board());

        assertTrue(board.getTitle().equals(board2.getTitle()));
    }


    @Test
    @Order(2)
    @DisplayName("게시판 조회 테스트")
    void selectBoardTest() {
//        Board board = boardRepository.findById(1L).get(); // 값이 없을 때 예외 발생
        Board board = boardRepository.findById(1L).orElse(null);
        System.out.println("board = " + board);
        assertNotNull(board);
    }

    @Test
    @Order(1)
    @DisplayName("게시판 등록 테스트")
    void insertBoardTest() {
        Board board = new Board();
        board.setTitle("Title");
        board.setContent("Content");
//        board.setWriter("Writer");
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());
        boardRepository.save(board);
    }

}