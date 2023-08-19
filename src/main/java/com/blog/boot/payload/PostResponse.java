package com.blog.boot.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Post Response Model information"
)
public class PostResponse {

    @Schema(
            description = "Blog Post Response Content"
    )
    private List<PostDto> content;

    @Schema(
            description = "Blog Post Response PageSize"
    )
    private int pageSize;

    @Schema(
            description = "Blog Post Response PageNo"
    )
    private int pageNo;

    @Schema(
            description = "Blog Post Response TotalElements"
    )
    private long totalElements;

    @Schema(
            description = "Blog Post Response TotalPages"
    )
    private int totalPages;

    @Schema(
            description = "Blog Post Response LastPage"
    )
    private boolean lastPage;
}
