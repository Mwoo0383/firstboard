package com.minu.firstboard.controller;

import com.minu.firstboard.entity.Board;
import com.minu.firstboard.entity.User;
import com.minu.firstboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시판 페이지 조회
    @GetMapping("/list")
    public String getList(Model model,
                          @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
        // 페이지 정보 생성 및 데이터 조회
        Page<Board> boardPage = boardService.getList(pageable);

        // 페이지의 데이터와 페이징 정보 전달
        model.addAttribute("list", boardPage.getContent());
        model.addAttribute("page", boardPage);

        return "board/list";
    }

    // 특정 게시판 조회
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

    // 게시판 삭제
    @PostMapping("/remove")
    public String remove(Long bno) {
        boardService.remove(bno);
        return "redirect:/board/list";
    }

    // 게시판 작성
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
        board.setViewCnt(0L);
        board.setInDate(LocalDate.now());
        board.setUpDate(LocalDate.now());
        boardService.write(board);

        return "redirect:/board/list";
    }

    // 게시판 수정
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

    // 게시판 검색
    @GetMapping("/keyword")
    public String search(@RequestParam(name = "keyword") String keyword, Model model) {
        List<Board> searchResults = boardService.searchBoard(keyword);
        model.addAttribute("list", searchResults != null ? searchResults : Collections.emptyList());
        return "board/list";
    }
}
