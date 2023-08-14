package com.blog.boot.payload;

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
public class CommentDto {
    private long id;

    // name should not be empty
    @NotEmpty
    private String name;

    // email should not be empty
    // email should be in correct format
    @NotEmpty
    @Email
    private String email;

    // body should not be empty
    // body should be at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Comment body should be at least 10 characters")
    private String body;
}
