package com.study.joiner.repository;

import com.study.joiner.domain.user.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
