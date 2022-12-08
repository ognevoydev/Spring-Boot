package com.ognevoydev.quisell.controller;

import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PostEditController {

    private final PostService postService;

    @PostMapping("/new")
    public void addPost(@RequestBody Post post) {
        postService.savePost(post);
    }

}
