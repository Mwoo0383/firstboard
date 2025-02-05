package com.minu.firstboard.dto.response;

import com.minu.firstboard.entity.Board;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BoardResponse {

    private long bno;
    private String title;
    private String content;
    private String userId;
    private long viewCnt;
    private LocalDate inDate;

    public BoardResponse(Board board) {
        this.bno = board.getBno();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUser().getId(); // User 엔티티에서 userId 가져오기
        this.viewCnt = board.getViewCnt();
        this.inDate = board.getInDate();
    }
}
