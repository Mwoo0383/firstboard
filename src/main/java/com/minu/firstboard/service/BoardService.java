package com.minu.firstboard.service;

import com.minu.firstboard.entity.Board;
import com.minu.firstboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 페이지네이션 메서드
    public Page<Board> getList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 현재 게시물을 가져오는 메서드
    public List<Board> getList() {
        return (List<Board>) boardRepository.findAll();
    }

    // 게시물을 작성하는 메서드
    public Board write(Board board) {
        return boardRepository.save(board);
    }

    // 게시물을 읽어오는 메서드
    public Board read(Long bno) {
        return boardRepository.findById(bno).orElse(null);
    }

    // 게시물을 수정하는 메서드
    public Board modify(Board newBoard) {
        Board board = boardRepository.findById(newBoard.getBno()).orElse(null);

        if (board == null) return null;
        board.setTitle(newBoard.getTitle());
        board.setContent(newBoard.getContent());

        return boardRepository.save(board);
    }

    // 게시물을 삭제하는 메서드
    public void remove(Long bno) {
         Board board = boardRepository.findById(bno).orElse(null);
         if (board != null) {
             boardRepository.delete(board);
         }
    }

    // 키워드 검색 메서드
    public List<Board> searchBoard(String keyword) {
        return boardRepository.searchByKeyword(keyword);
    }
}
