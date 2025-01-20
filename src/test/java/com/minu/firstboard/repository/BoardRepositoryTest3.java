package com.minu.firstboard.repository;

import com.minu.firstboard.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest3 {

    @Autowired
    public EntityManager em;

    @Autowired
    private BoardRepository boardRepository;


    @BeforeEach
    public void testData() {
        for (int i = 1; i <= 100; i++) {
            Board board = new Board();
            board.setTitle("Title" + i);
            board.setContent("Content" + i);
            board.setViewCnt((long)(Math.random() * 100));
            board.setInDate(new Date());
            board.setUpDate(new Date());
            boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("createQuery로 JPQL작성 테스트")
    public void createQueryTest() {
        String query = "SELECT b FROM Board b";
        TypedQuery<Board> tQuery = em.createQuery(query, Board.class);
        List<Board> list = tQuery.getResultList();

        assertTrue(list.size() == 100);
    }
//
//    @Test
//    @DisplayName("@Query로 JPQL작성 테스트")
//    public void QueryAnnoTest() {
//        List<Board> list = boardRepository.findAllBoard();
//        assertTrue(list.size() == 100);
//    }

//    @Test
//    @DisplayName("@Query로 JPQL작성 테스트 - 매개변수 순서")
//    public void QueryAnnoTest2() {
//        List<Board> list = boardRepository.findByTitleAndWriter2("title1", "writer1");
//        assertTrue(list.size() == 1);
//    }
//
//    @Test
//    @DisplayName("@Query로 JPQL작성 테스트 - 매개변수 이름")
//    public void QueryAnnoTest3() {
//        List<Board> list = boardRepository.findByTitleAndWriter2("title1", "writer1");
//        assertTrue(list.size() == 1);
//    }
//
//    @Test
//    @DisplayName("@Query로 JPQL작성 테스트 - 매개변수 이름")
//    public void QueryAnnoTest4() {
//        List<Board> list = boardRepository.findByTitleAndWriter2("title1", "writer1");
//        assertTrue(list.size() == 1);
//    }
//
//    @Test
//    @DisplayName("@Query로 네이티브 쿼리(SQL)작성 테스트")
//    public void QueryAnnoTest5() {
////        List<Board> list = boardRepository.findAllBoardBySQL();
////        assertTrue(list.size() == 100);
//        List<Object[]> list = boardRepository.findAllBoardBySQL2();
//        list.stream().map(arr -> Arrays.toString(arr)).forEach(System.out::println);
//        assertTrue(list.size() == 100);
//    }
}