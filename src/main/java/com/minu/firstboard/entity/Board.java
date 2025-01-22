package com.minu.firstboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bno = 1;

    private String title;

    private String content;

    @ManyToOne // 여러 Board에 하나의 User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private long viewCnt;

    private LocalDate inDate;

    private LocalDate upDate;

    @Override
    public String toString() {
        return "Board{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", viewCnt=" + viewCnt +
                ", inDate=" + inDate +
                ", upDate=" + upDate +
                '}';
    }
}
