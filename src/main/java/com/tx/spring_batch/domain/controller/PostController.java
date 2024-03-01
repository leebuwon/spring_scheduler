package com.tx.spring_batch.domain.controller;

import com.tx.spring_batch.domain.dto.request.CreatePostRequest;
import com.tx.spring_batch.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/v1/create")
    public ResponseEntity<Void> create(@RequestBody CreatePostRequest createPostRequest){
        postService.create(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/delete/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId){
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
