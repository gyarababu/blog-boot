package com.blog.boot.service;

import com.blog.boot.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
