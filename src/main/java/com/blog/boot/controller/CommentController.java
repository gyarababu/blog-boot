package com.blog.boot.controller;

import com.blog.boot.payload.CommentDto;
import com.blog.boot.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@Tag(
        name = "Comment Resource CRUD REST APIs"
)
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment REST API",
            description = "Saving comment data into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    // create comment REST API
    // http://localhost:8080/api/posts/1/comments
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Comments REST API",
            description = "Get all comments data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get all comments by post id REST API
    // http://localhost:8080/api/posts/1/comments
    @GetMapping
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(name = "postId") long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @Operation(
            summary = "Get Comment By Id REST API",
            description = "Get single comment data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get comment by comment id and post id REST API
    // http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId,
                                                         @PathVariable(name = "commentId") long commentId){

        // convert both parameters to single DTO
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment By Id REST API",
            description = "Update single comment data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // update comment REST API
    // http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") long postId,
                                                    @PathVariable(name = "commentId")long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete comment By Id REST API",
            description = "Delete single comment data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // delete comment REST API
    // http://localhost:8080/api/posts/1/comments/1
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") long postId,
                                                @PathVariable(name = "commentId")long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

}
