package com.blog.boot.service.impl;

import com.blog.boot.entity.Post;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.PostDto;
import com.blog.boot.repository.PostRepository;
import com.blog.boot.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);

        // saving the entity in table
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);

        // sending the DTO to Rest endpoint
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        // finding all posts
        List<Post> posts = postRepository.findAll();

        // converting to DTO
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        // finding the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",id)
        );

        // converting to DTO
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePostById(Long id, PostDto postDto) {
        // finding the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        // updating the details
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // saving the details
        Post updatedPost = postRepository.save(post);

        // converting to DTO
        return mapToDTO(updatedPost);
    }


    // convert entity to DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
