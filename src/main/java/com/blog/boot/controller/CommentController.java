package com.blog.boot.controller;

import com.blog.boot.payload.CommentDto;
import com.blog.boot.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
}
