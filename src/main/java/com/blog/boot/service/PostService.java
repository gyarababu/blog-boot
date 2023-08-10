package com.blog.boot.service;

import com.blog.boot.payload.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto);
}
