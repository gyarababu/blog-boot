package com.blog.boot.service.impl;

import com.blog.boot.entity.Comment;
import com.blog.boot.entity.Post;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.CommentDto;
import com.blog.boot.repository.CommentRepository;
import com.blog.boot.repository.PostRepository;
import com.blog.boot.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        // convert commentDto to entity
        Comment comment = mapToEntity(commentDto);

        // find post to assign the comment to post
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // set post to comment entity
        comment.setPost(post);

        // save the comment entity
        Comment newComment = commentRepository.save(comment);

        // covert entity to DTO and return
        return mapToDTO(newComment);
    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
