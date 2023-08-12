package com.blog.boot.repository;

import com.blog.boot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentsByPostId(long postId);
}
