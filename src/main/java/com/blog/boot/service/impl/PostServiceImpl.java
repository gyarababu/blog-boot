package com.blog.boot.service.impl;

import com.blog.boot.entity.Category;
import com.blog.boot.entity.Post;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.PostDto;
import com.blog.boot.payload.PostResponse;
import com.blog.boot.repository.CategoryRepository;
import com.blog.boot.repository.PostRepository;
import com.blog.boot.service.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        logger.info("started create post service class for user info log level ");

        // find category id
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTO to entity
        Post post = mapToEntity(postDto);

        // before saving the post set the category
        post.setCategory(category);

        // saving the entity in table
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        logger.info("ended create post service class for user info log level ");

        // sending the DTO to Rest endpoint
        return postResponse;
    }

    @Override
    // changing List<PostDto> to PostResponse since returning PostResponse for paging support
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info("started get all posts service class for user info log level ");

        // sorting instance for both sorting parameters
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create pageable instance combining two parameters into one
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // finding all posts, but it's in the form of Page
        Page<Post> posts = postRepository.findAll(pageable);

        // covert the page to List using page method
        List<Post> listOfPosts = posts.getContent();

        // converting to DTO as content for paging support
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        // creating PostResponse object and setting all the variables or values to it
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageSize(posts.getSize());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());
        logger.info("ended get all posts service class for user info log level ");

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        logger.info("started get post service class for user info log level ");

        // finding the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",id)
        );
        logger.info("ended create post service class for user info log level ");

        // converting to DTO
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        logger.info("started update post service class for user info log level ");

        // finding the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        // find category id
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // updating the details
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        //before saving the post
        post.setCategory(category);

        // saving the details
        Post updatedPost = postRepository.save(post);
        logger.info("ended update post service class for user info log level ");

        // converting to DTO
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        logger.info("started delete post service class for user info log level ");

        // find the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        // delete post by id
        postRepository.delete(post);
        logger.info("ended delete post service class for user info log level ");

    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        logger.info("started get posts by category service class for user info log level ");

        // find category id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));

        // find posts by category id
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        logger.info("ended get posts by category service class for user info log level ");

        // convert entity to dto
        return posts.stream().map((post) -> mapToDTO(post)).collect(Collectors.toList());
    }


    // convert entity to DTO
    private PostDto mapToDTO(Post post){

        // Using mapper
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){

        // Using mapper
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

}
