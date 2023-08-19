package com.blog.boot.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Schema(
        description = "PostDTO Model information"
)
public class PostDto {
    private Long id;

    @Schema(
            description = "Blog Post Title"
    )
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post Description"
    )
    // description should not be null or empty
    // description should have at least 2 characters
    @NotEmpty
    @Size(min = 10, message = "Post Description should have at least 10 characters")
    private String description;

    @Schema(
            description = "Blog Post Content"
    )
    // content should not be null or empty
    @NotEmpty
    private String content;

    @Schema(
            description = "Blog Post Comments"
    )
    // getting all comments for post
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post Category"
    )
    // we get category in dropdown and each category is specified with id
    private long categoryId;
}
