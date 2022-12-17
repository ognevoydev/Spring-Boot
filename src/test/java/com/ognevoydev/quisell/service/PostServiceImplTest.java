package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class PostServiceImplTest {

    private static Post post;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    static void beforeAll() {
        post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);
    }

    @Test
    void deletePostById() {
        postService.savePost(post);

        assertThrows(HttpStatusException.class, () -> postService.deletePostById(UUID.randomUUID()));
    }

    @Test
    void isPostOwner() {
        postService.savePost(post);

        assertTrue(postService.isPostOwner(post.getId(), post.getAccountId()));
        assertFalse(postService.isPostOwner(post.getId(), UUID.randomUUID()));
        assertThrows(HttpStatusException.class, () -> postService.isPostOwner(UUID.randomUUID(), post.getAccountId()));
    }
}