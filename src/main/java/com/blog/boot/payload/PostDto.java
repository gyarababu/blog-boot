package com.blog.boot.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
public class PostDto {
    private Long id;

    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;

    // description should not be null or empty
    // description should have at least 2 characters
    @NotEmpty
    @Size(min = 10, message = "Post Description should have at least 10 characters")
    private String description;

    // content should not be null or empty
    @NotEmpty
    private String content;

    // getting all comments for post
    private Set<CommentDto> comments;
}
