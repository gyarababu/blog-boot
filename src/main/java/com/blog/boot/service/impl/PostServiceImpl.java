package com.blog.boot.service.impl;

import com.blog.boot.entity.Post;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.PostDto;
import com.blog.boot.payload.PostResponse;
import com.blog.boot.repository.PostRepository;
import com.blog.boot.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    // changing List<PostDto> to PostResponse since returning PostResponse for paging support
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

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
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        // finding the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",id)
        );

        // converting to DTO
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
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

    @Override
    public void deletePostById(long id) {
        // find the post by id
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        // delete post by id
        postRepository.delete(post);
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
