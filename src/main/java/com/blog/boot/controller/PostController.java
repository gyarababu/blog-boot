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

    // get all posts Rest API
    // http://localhost:8080/api/posts
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    // get post by id REST API
    // http://localhost:8080/api/posts/1
    // Need to pass values or parameters in Flower braces - {}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
       // return new ResponseEntity<PostDto>(postService.getPostById(id), HttpStatus.OK);
        // instead we can write like this
        return ResponseEntity.ok(postService.getPostById(id));

    }

    // update post by id REST API
    // http://localhost:8080/api/posts/1
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable(name = "id") Long id,
                                                  @RequestBody PostDto postDto){
        // since we have two objects, we are passing them together as one object - postResponse
        PostDto postResponse = postService.updatePostById(id, postDto);
        return new ResponseEntity<PostDto>(postResponse, HttpStatus.OK);
    }

    // delete post by id REST API
    // http://localhost:8080/api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }
}
