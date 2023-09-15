package com.blog.boot.service.impl;

import com.blog.boot.entity.Comment;
import com.blog.boot.entity.Post;
import com.blog.boot.exception.BlogAPIException;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.CommentDto;
import com.blog.boot.repository.CommentRepository;
import com.blog.boot.repository.PostRepository;
import com.blog.boot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        logger.info("started create comment service class for user info log level ");

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
        logger.info("ended create comment service class for user info log level ");

        // covert entity to DTO and return
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        logger.info("started get all comments service class for user info log level ");

        // find list of comments by post id
        List<Comment> comments = commentRepository.findAllCommentsByPostId(postId);
        logger.info("ended get all comments service class for user info log level ");

        // convert each comment to DTO and return
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        logger.info("started get comment service class for user info log level ");

        // find post by Id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // find comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        // compare both and exception if comment does not belong to post
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }
        logger.info("ended get comment service class for user info log level ");

        // convert to DTO and return
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        logger.info("started update comment service class for user info log level ");

        // find post by Id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // find comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        // compare both and exception if comment does not belong to post
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        // setting variables to DTO
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        // saving values
        Comment updatedComment = commentRepository.save(comment);
        logger.info("ended update comment service class for user info log level ");

        // convert to DTO and return
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        logger.info("started delete comment service class for user info log level ");

        // find post by Id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // find comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        // compare both and exception if comment does not belong to post
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        commentRepository.delete(comment);
        logger.info("ended delete comment service class for user info log level ");

    }


    // convert entity to DTO
    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    // convert DTO to entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
