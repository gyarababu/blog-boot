package com.blog.boot.service;

import com.blog.boot.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePostById(Long id, PostDto postDto);


    void deletePostById(Long id);
}
