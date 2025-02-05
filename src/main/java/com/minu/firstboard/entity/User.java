package com.minu.firstboard.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    // FetchType.EAGER - 두 엔티티의 정보를 같이 가져오는 것
    // FetchTpye.LAZY - 따로 가져오는 것, 나중에 getList().
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // user하나에 여러 board
    List<Board> list = new ArrayList<>();

    private Date inDate;  // 입력일

    private Date upDate;  // 변경일

}
