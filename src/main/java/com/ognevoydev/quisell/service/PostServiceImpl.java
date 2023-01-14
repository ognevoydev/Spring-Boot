package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.NotFoundException;
import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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

    @Override
    public List<Post> getAllPosts () {
        return postRepository.findActivePostsByDeletedAtIsNull();
    }

    @Transactional
    @Override
    public void savePost(Post post) {
        UUID accountId = UUID.randomUUID(); //TODO привязать аккаунт
        post.setAccountId(accountId);
        postRepository.save(post);
    }

    @Override
    public boolean isPostOwner(UUID postId, UUID accountId) {
        return postRepository.isPostOwner(postId, accountId).orElseThrow(
                () -> new NotFoundException(postId, Post.class)
        );
    }

    @Transactional
    @Override
    public void deletePostById(UUID postId) {
        if(postRepository.setDeletedAt(new Date(), postId) < 1)
            throw new NotFoundException(postId, Post.class);
    }

    @Transactional
    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

}
