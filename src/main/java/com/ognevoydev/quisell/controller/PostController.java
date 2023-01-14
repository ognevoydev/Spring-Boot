package com.ognevoydev.quisell.controller;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.dto.PostUpdateDTO;
import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.model.mapper.PostMapper;
import com.ognevoydev.quisell.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable(value = "id") UUID postId) {
        return postService.getPostById(postId);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public void addPost(@RequestBody Post post) {
        postService.savePost(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable(value = "id") UUID postId, Principal principal) {

        if(postService.isPostOwner(postId, UUID.fromString(String.valueOf(principal)))) {
            postService.deletePostById(postId);
        }
        else {
            throw new HttpStatusException("Access to this resource on the server is denied", FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable(value = "id") UUID postId,
                           @RequestBody PostUpdateDTO updateDTO,
                           Principal principal) {
        if(postService.isPostOwner(postId, UUID.fromString(String.valueOf(principal)))) {
            Post post = postService.getPostById(postId);
            postMapper.updatePost(post, updateDTO);
            postService.updatePost(post);
        }
        else {
            throw new HttpStatusException("Access to this resource on the server is denied", FORBIDDEN);
        }
    }

}
