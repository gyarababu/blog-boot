package com.blog.boot.payload;

import lombok.*;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;

    // getting all comments for post
    private Set<CommentDto> comments;
}
