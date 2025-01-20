package com.minu.firstboard.repository;

import com.minu.firstboard.entity.Board;
import com.minu.firstboard.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManyToOneTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void ManyToOneTest() {
        User user = new User();
        user.setId("aaa");
        user.setName("LEE");
        user.setPassword("123");
        user.setEmail("lee@gmail.com");
        user.setInDate(new Date());
        user.setUpDate(new Date());
        userRepository.save(user);
        em.flush();

        Board board1 = new Board();
        board1.setTitle("title1");
        board1.setContent("content1");
        board1.setUser(user);
        board1.setViewCnt(0L);
        board1.setInDate(new Date());
        board1.setUpDate(new Date());
        boardRepository.save(board1);

        // User의 list에 Board 추가
        user.getList().add(board1);

        Board board2 = new Board();
        board2.setTitle("title2");
        board2.setContent("content2");
        board2.setUser(user);
        board2.setViewCnt(0L);
        board2.setInDate(new Date());
        board2.setUpDate(new Date());
        boardRepository.save(board2);

        // User의 list에 Board 추가
        user.getList().add(board2);

        em.flush();
        em.clear();

        board1 = boardRepository.findById(1L).orElse(null);
        board2 = boardRepository.findById(2L).orElse(null);

        System.out.println("board1 = " + board1);
        System.out.println("board2 = " + board2);

        assertNotNull(board1);
        assertNotNull(board2);

        user = userRepository.findById(user.getId()).orElse(null);
        System.out.println("user = " + user);
        assertNotNull(user);
    }
}