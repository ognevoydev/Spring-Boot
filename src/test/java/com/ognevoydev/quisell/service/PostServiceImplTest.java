package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PostServiceImplTest {

    private static Post post;
    private static UUID testId;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    static void beforeAll() { //creating post
        post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);
    }

    @BeforeEach
    public void beforeEach() { //changing post id before each test
        testId = UUID.randomUUID();
        post.setId(testId);
        post.setAccountId(testId);
    }

    @AfterEach
    public void afterEach() { //deleting post after each test
        postRepository.deleteById(testId);
    }

    @Test
    void deletePostById() {
        saveTestPost(post);

        assertThrows(HttpStatusException.class, () -> postService.deletePostById(UUID.randomUUID()));
    }

    @Test
    void isPostOwner() {
        saveTestPost(post);

        assertTrue(postService.isPostOwner(post.getId(), post.getAccountId()));
        assertFalse(postService.isPostOwner(post.getId(), UUID.randomUUID()));
        assertThrows(HttpStatusException.class, () -> postService.isPostOwner(UUID.randomUUID(), post.getAccountId()));
    }

    public void saveTestPost(Post post) { //saving post into db with test id
        postRepository.saveTestPost(post.getId(), post.getAccountId(), post.getTitle(), post.getDescription(), post.getUsed(), Instant.now(), Instant.now());
    }
}