package com.minu.firstboard.controller;

import com.minu.firstboard.entity.Board;
import com.minu.firstboard.entity.User;
import com.minu.firstboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String getList(Model model, Integer page, Integer size) {
        // 기본값 설정
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 5; // 원하는 기본 페이지 크기
        }

        // 페이지 정보 생성 (0부터 시작)
        Pageable pageable = PageRequest.of(page, size, Sort.by("bno").descending());
        Page<Board> boardPage = boardService.getList(pageable);

        // 페이지 데이터와 페이징 정보 전달
        model.addAttribute("list", boardPage.getContent()); // 현재 페이지 데이터
        model.addAttribute("page", boardPage);             // 전체 페이징 정보

        return "board/list";
    }

    @GetMapping("/read")
    public String read(Long bno, Model model) {
        Board board = boardService.read(bno);
        model.addAttribute("board", board);
        return "board/read";
    }

//    @GetMapping("/list")
//    public String getList(Model model) {
//        List<Board> list = boardService.getList();
//        model.addAttribute("list", list);
//
//        return "board/list";
//    }

    @PostMapping("/remove")
    public String remove(Long bno) {
        boardService.remove(bno);
        return "redirect:/board/list";
    }

    @GetMapping("/write")
    public String showWriteForm(Model model) {
        Board board = new Board();
        User user = new User();
        user.setId("aaa");
        board.setUser(user);

        model.addAttribute("board", board);
        return "board/write";
    }

    @PostMapping("/write")
    public String write(Board board) {
//        User user = new User();
//        user.setId("aaa");
//        board.setUser(user);
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());
        boardService.write(board);

        return "redirect:/board/list";
    }

    @GetMapping("/modify")
    public String modify(Long bno, Model model) {
        Board board = boardService.read(bno);
        model.addAttribute("board", board);

        return "board/write";
    }

    @PostMapping
    public String modify(Board board) {
        boardService.modify(board);

        return "redirect:/board/list";
    }
}
