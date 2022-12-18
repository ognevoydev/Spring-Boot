package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void deletePostById() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertThrows(HttpStatusException.class, () -> postService.deletePostById(UUID.randomUUID()));

        List<Post> activePosts = postRepository.findActivePostsByDeletedAtIsNull();
        UUID postToBeDeletedId = activePosts.get(0).getId();
        postService.deletePostById(postToBeDeletedId);

        assertNotNull(postService.getPostById(postToBeDeletedId).getDeletedAt());
    }

    @Test
    void isPostOwner() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertTrue(postService.isPostOwner(post.getId(), post.getAccountId()));
        assertFalse(postService.isPostOwner(post.getId(), UUID.randomUUID()));
        assertThrows(HttpStatusException.class, () -> postService.isPostOwner(UUID.randomUUID(), post.getAccountId()));
    }
}