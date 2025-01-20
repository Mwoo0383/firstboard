package com.minu.firstboard.service;

import com.minu.firstboard.entity.Board;
import com.minu.firstboard.entity.User;
import com.minu.firstboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getListTest() {
        List<Board> list = boardService.getList();
        System.out.println("list = " + list);
        assertEquals(list.size(), 10);
    }

    @Test
    void modifyTest() {
        Board board = boardService.read(1L);
        board.setTitle("modify title");
        board.setContent("modify content");
        boardService.modify(board);

        Board board2 = boardService.read(1L);
        assertEquals(board.getTitle(), board2.getTitle());
        assertEquals(board.getContent(), board2.getContent());
    }

    @Test
    void deleteTest() {
        Long testBno = 5L;

        assertTrue(boardService.read(testBno)!=null);
        boardService.remove(testBno);
        assertEquals(boardService.read(testBno), null);
    }

    @Test
    void writeAndReadTest() {
        User user = new User();
        user.setId("bbb");
        userRepository.save(user);

        Board board = new Board();
        board.setTitle("new title");
        board.setContent("new content");
        board.setUser(user);
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());
        boardService.write(board);

        Board board2 = boardService.read(11L);
        assertTrue(board2 !=null);
        assertEquals(board.getTitle(), board2.getTitle());
        assertEquals(board.getContent(), board2.getContent());
    }

    @BeforeEach
    public void init() {
        for (Long i = 1L; i <= 10 ; i++) {
            Board board = new Board();
            board.setTitle("title" + i);
            board.setContent("content" + i);
            User user = new User();
            user.setId("aaa");
            userRepository.save(user);

            board.setUser(user);
            board.setViewCnt(0L);
            board.setInDate(new Date());
            board.setUpDate(new Date());
            boardService.write(board);
        }
    }
}