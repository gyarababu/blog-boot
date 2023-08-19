package com.blog.boot.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "CommentDTO Model information"
)
public class CommentDto {
    private long id;

    @Schema(
            description = "Blog Comment Name"
    )
    // name should not be empty
    @NotEmpty
    private String name;

    @Schema(
            description = "Blog Comment Email"
    )
    // email should not be empty
    // email should be in correct format
    @NotEmpty
    @Email
    private String email;

    @Schema(
            description = "Blog Comment Body"
    )
    // body should not be empty
    // body should be at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Comment body should be at least 10 characters")
    private String body;
}
