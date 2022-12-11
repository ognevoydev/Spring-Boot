package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.NotFoundException;
import com.ognevoydev.quisell.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import com.ognevoydev.quisell.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public Post getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(postId, Post.class));
        if(post.getDeletedAt() != null) throw new NotFoundException(postId, Post.class);
        else return post;
    }

    @Override
    public List<Post> getAllPosts () {
        return postRepository.findAllPosts();
    }

    @Transactional
    @Override
    public void savePost(Post post) {
        UUID accountId = UUID.randomUUID(); //TODO привязать аккаунт
        post.setAccountId(accountId);
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void deletePostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(postId, Post.class));
        post.setDeletedAt(Instant.now());
        postRepository.save(post);
    }

}
