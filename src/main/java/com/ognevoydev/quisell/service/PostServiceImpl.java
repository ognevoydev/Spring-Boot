package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.NotFoundException;
import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public Post getPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(postId, Post.class));
    }

}
