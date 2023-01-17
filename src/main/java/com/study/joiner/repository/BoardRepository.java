package com.study.joiner.repository;


import com.study.joiner.domain.user.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b order by b.createdDate desc")
    List<Board> findAllDesc();

    @Query("select b from Board b order by b.createdDate desc")
    Page<Board> findAllDesc(Pageable pageable);
}
