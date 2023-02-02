package com.study.joiner.repository;


import com.study.joiner.domain.user.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"socialUser", "commentList.user"})
    Optional<Board> findById(Long id);

    @Query("select b from Board b order by b.createdDate desc")
    Page<Board> findAllDesc(Pageable pageable);

    @Query("select b from Board b left join b.socialUser u where u.email = :email order by b.createdDate desc")
    Page<Board> findAllByEmail(String email, Pageable pageable);
}
