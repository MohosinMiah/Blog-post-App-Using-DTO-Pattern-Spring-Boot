package com.post.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.post.post.payload.PostDto;
import com.post.post.service.PostService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

    @Autowired
    private PostService postService;



    // create blog post rest api
    @PostMapping("posts")
    public PostDto createPost(@Valid @RequestBody PostDto postDto){
        return postService.createPost(postDto, HttpStatus.CREATED);
    }


}
