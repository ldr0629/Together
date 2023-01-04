package com.study.joiner.repository;


import com.study.joiner.domain.user.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
