package com.blog.boot.service;

import com.blog.boot.payload.PostDto;
import com.blog.boot.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePostById(long id, PostDto postDto);

    void deletePostById(long id);

    List<PostDto> getPostsByCategory(long categoryId);
}
