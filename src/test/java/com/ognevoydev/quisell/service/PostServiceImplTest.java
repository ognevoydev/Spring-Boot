package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.model.mapper.PostMapper;
import com.ognevoydev.quisell.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:clean_file.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Test
    void deletePostCheckThrowsExceptionIfNoPostExist() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertThrows(HttpStatusException.class, () -> postService.deletePostById(UUID.randomUUID()));
    }

    @Test
    void deletePostCheckIsNotNullIfPostDeleted() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);
        postService.deletePostById(post.getId());

        assertNotNull(postService.getPostById(post.getId()).getDeletedAt());
    }

    @Test
    void postOwnerCheckIsTrueIfOwner() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertTrue(postService.isPostOwner(post.getId(), post.getAccountId()));
    }

    @Test
    void postOwnerCheckIsFalseIfNotAnOwner() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertFalse(postService.isPostOwner(post.getId(), UUID.randomUUID()));
    }

    @Test
    void postOwnerCheckThrowsExceptionIfNoPostExist() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertThrows(HttpStatusException.class, () -> postService.isPostOwner(UUID.randomUUID(), post.getAccountId()));
    }

    @Test
    void getPostByIdCheckThrowsExceptionIfNoPostExist() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertThrows(HttpStatusException.class, () -> postService.getPostById(UUID.randomUUID()));
    }

    @Test
    void getPostByIdCheckIsNotNullIfPostExist() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertNotNull(postService.getPostById(post.getId()));
    }

    @Test
    void getPostByIdCheckIfSavedPostEqualsToFound() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);
        Post found = postService.getPostById(post.getId());

        assertEquals(post, found);
    }

    @Test
    void getAllPostsCheckIsTrueIfNoPostsExist() {
        assertTrue(postService.getAllPosts().isEmpty());
    }

    @Test
    void getAllPostsCheckIsNotNullIfPostsExist() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);
        List<Post> posts = postService.getAllPosts();

        assertNotNull(posts);
    }

    @Test
    void getAllPostsCheckIsTrueIfPostsFoundDeletedAtIsNull() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);
        List<Post> posts = postService.getAllPosts();

        assertTrue(posts.stream().allMatch(p -> p.getDeletedAt() == null));
    }

    @Test
    void savePostCheckIfSavedPostEqualsToFound() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertEquals(post, postService.getPostById(post.getId()));
    }
}