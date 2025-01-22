package com.minu.firstboard.repository;

import com.minu.firstboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // JPQL
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    List<Board> searchByKeyword(@Param("keyword") String keyword);

    // 쿼리 메서드
//    List<Board> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}
