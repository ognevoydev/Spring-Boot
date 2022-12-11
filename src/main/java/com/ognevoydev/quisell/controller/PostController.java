package com.ognevoydev.quisell.controller;

import com.ognevoydev.quisell.common.exception.ForbiddenException;
import com.ognevoydev.quisell.common.exception.NotFoundException;
import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.service.PostService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

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
//      String postAccountId = postService.getPostById(postId).getAccountId().toString();
//        if(principal.toString().equals(postAccountId)){
            postService.deletePostById(postId);
//        }
//        else {
//            throw new ForbiddenException(postId, Post.class);
//        }
    }

}
