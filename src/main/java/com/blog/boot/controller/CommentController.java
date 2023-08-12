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
}
