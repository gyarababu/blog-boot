package com.blog.boot.service.impl;

import com.blog.boot.entity.Post;
import com.blog.boot.payload.PostDto;
import com.blog.boot.repository.PostRepository;
import com.blog.boot.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToDTO(postDto);

        // saving the entity in table
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToEntity(newPost);

        // sending the DTO to Rest endpoint
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return null;
    }

    // convert DTO to entity
    private PostDto mapToEntity(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert entity to DTO
    private Post mapToDTO(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
