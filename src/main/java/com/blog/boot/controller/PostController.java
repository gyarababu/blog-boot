package com.blog.boot.controller;
import com.blog.boot.payload.PostDto;
import com.blog.boot.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    // we are using interface for LooseCoupling.
    // We are not using classes or Implementation which gives TightCoupling
    private PostService postService;

    // If we are using class, and it has only one constructor
    // then we can omit @Autowired annotation
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create post Rest API
    // http://localhost:8080/api/posts
    // @RequestBody annotation converts JSON into Java object or else null values will be stored
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        // we are passing PostDto or else we will be getting Object as Type in service
        // we are passing HttpsStatus as 2nd Parameter
        return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return new ResponseEntity<List<PostDto>>(postService.getAllPosts(), HttpStatus.OK);
    }

}
