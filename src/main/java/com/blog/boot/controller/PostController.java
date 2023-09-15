package com.blog.boot.controller;
import com.blog.boot.payload.PostDto;
import com.blog.boot.payload.PostResponse;
import com.blog.boot.service.PostService;
import com.blog.boot.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Post Resource CRUD REST APIs"
)
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);

    // we are using interface for LooseCoupling.
    // We are not using classes or Implementation which gives TightCoupling
    private PostService postService;

    // If we are using class, and it has only one constructor
    // then we can omit @Autowired annotation
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "Saving post data into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    // create post Rest API
    // http://localhost:8080/api/posts
    // @RequestBody annotation converts JSON into Java object or else null values will be stored
    // creating authorization header to the REST API in swagger
    @SecurityRequirement(
            name = "Authentication Header"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts") // versioning
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        logger.info("started create post controller for user info log level ");

        // we are passing PostDto or else we will be getting Object as Type in service
        // we are passing HttpsStatus as 2nd Parameter
        return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Posts REST API",
            description = "Get all posts data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get all posts Rest API
    // http://localhost:8080/api/posts
    @GetMapping("/posts")
    public PostResponse getAllPosts(@RequestParam(name = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER,
                                            required = false) int pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE,
                                            required = false) int pageSize,
                                    @RequestParam(name = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY,
                                            required = false) String sortBy,
                                    @RequestParam(name = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION,
                                            required = false) String sortDir
    ){
        logger.info("started get all posts controller for user info log level ");

        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get single post data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get post by id REST API
    // http://localhost:8080/api/posts/1
    // Need to pass values or parameters in Flower braces - {}
    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") long id){
        logger.info("started get post by id controller for user info log level ");

        // return new ResponseEntity<PostDto>(postService.getPostById(id), HttpStatus.OK);
        // instead we can write like this
        return ResponseEntity.ok(postService.getPostById(id));

    }

    @Operation(
            summary = "Update Post By Id REST API",
            description = "Update single post data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // update post by id REST API
    // http://localhost:8080/api/posts/1
    // creating authorization header to the REST API in swagger
    @SecurityRequirement(
            name = "Authentication Header"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{id}") // versioning
    public ResponseEntity<PostDto> updatePostById(@PathVariable(name = "id") long id,
                                                  @Valid @RequestBody PostDto postDto){
        logger.info("started put post by id controller for user info log level ");

        // since we have two objects, we are passing them together as one object - postResponse
        PostDto postResponse = postService.updatePostById(id, postDto);
        return new ResponseEntity<PostDto>(postResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete single post data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // delete post by id REST API
    // http://localhost:8080/api/posts/1
    // creating authorization header to the REST API in swagger
    @SecurityRequirement(
            name = "Authentication Header"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}") // versioning
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
        logger.info("started delete post controller for user info log level ");

        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Posts By Category REST API",
            description = "Get All posts using category data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get posts by category id REST API
    // http://localhost:8080/api/posts/category/1
    @GetMapping("/posts/category/{id}") // versioning
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable("id") long categoryId){
        logger.info("started get all posts by category controller for user info log level ");

        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return  ResponseEntity.ok(postDtos);
    }
}
