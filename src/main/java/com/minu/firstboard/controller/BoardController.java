package com.minu.firstboard.controller;

import com.minu.firstboard.dto.response.BoardResponse;
import com.minu.firstboard.entity.Board;
import com.minu.firstboard.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    // 게시판 목록 조회 (페이징 포함)
    @Operation(summary = "게시판 목록 조회", description = "게시판 목록을 페이징하여 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/list")
    public ResponseEntity<Page<BoardResponse>> getList(
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page) {

        // 요청된 page가 1보다 작으면 1로 고정 (0을 방지)
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        // 클라이언트에서 page=1을 보내면 서버에서는 이를 0으로 변환해서 처리
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "bno"));
        Page<Board> boardPage = boardService.getList(pageable);
        Page<BoardResponse> responsePage = boardPage.map(BoardResponse::new); // DTO 변환

        // 페이지 번호를 1부터 시작하도록 변환
        Page<BoardResponse> adjustedResponsePage = new PageImpl<>(
                responsePage.getContent(),
                pageable,
                boardPage.getTotalElements()
        ) {
            @Override
            public int getNumber() {
                return super.getNumber() + 1; // 페이지 번호에 1을 더해주기
            }
        };

        return ResponseEntity.ok(adjustedResponsePage);
    }

    // 특정 게시글 조회
    @GetMapping("/{bno}")
    public ResponseEntity<Board> read(@PathVariable Long bno) {
        Board board = boardService.read(bno);
        return ResponseEntity.ok(board);
    }

    // 게시글 삭제
    @DeleteMapping("/{bno}")
    public ResponseEntity<Void> remove(@PathVariable Long bno) {
        boardService.remove(bno);
        return ResponseEntity.noContent().build();
    }

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Board> write(@RequestBody Board board) {
        board.setViewCnt(0L);
        board.setInDate(LocalDate.now());
        board.setUpDate(LocalDate.now());
        boardService.write(board);
        return ResponseEntity.ok(board);
    }

    // 게시글 수정
    @PutMapping("/{bno}")
    public ResponseEntity<Board> modify(@PathVariable Long bno, @RequestBody Board board) {
        board.setBno(bno);
        boardService.modify(board);
        return ResponseEntity.ok(board);
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<List<Board>> search(@RequestParam(name = "keyword") String keyword) {
        List<Board> searchResults = boardService.searchBoard(keyword);
        return ResponseEntity.ok(searchResults);
    }
}
