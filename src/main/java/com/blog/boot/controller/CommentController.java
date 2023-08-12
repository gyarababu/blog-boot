package com.blog.boot.controller;

import com.blog.boot.payload.CommentDto;
import com.blog.boot.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // create comment REST API
    // http://localhost:8080/api/posts/1/comments
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    // get all comments by post id REST API
    // http://localhost:8080/api/posts/1/comments
    @GetMapping
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(name = "postId") long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    // get comment by comment id and post id REST API
    // http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId,
                                                         @PathVariable(name = "commentId") long commentId){

        // convert both parameters to single DTO
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
    }

    // update comment REST API
    // http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") long postId,
                                                    @PathVariable(name = "commentId")long commentId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
    }
}
