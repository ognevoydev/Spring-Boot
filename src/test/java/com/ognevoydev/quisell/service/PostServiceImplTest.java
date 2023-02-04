package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.dto.PostUpdateDTO;
import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.model.mapper.PostMapper;
import com.ognevoydev.quisell.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @BeforeEach
    void cleanUp() {
        List<Post> posts = postRepository.findAll();
        for(Post post : posts) {
            postRepository.delete(post);
        }
    }

    @Test
    void deletePostById_IfNoPostExist_ThrowsException() {
        assertThrows(HttpStatusException.class, () -> postService.deletePostById(UUID.randomUUID()));
    }

    @Test
    void getPostById_IfPostDeleted_DeletedAtIsNotNull() {
        Post post = new Post("Foo", "Foo", false);

        postService.savePost(post);
        postService.deletePostById(post.getId());

        assertNotNull(postService.getPostById(post.getId()).getDeletedAt());
    }

    @Test
    void isPostOwner_IfOwner_ReturnsTrue() {
        Post post = new Post("Foo", "Foo", false);

        postService.savePost(post);

        assertTrue(postService.isPostOwner(post.getId(), post.getAccountId()));
    }

    @Test
    void isPostOwner_IfNotAnOwner_ReturnsFalse() {
        Post post = new Post("Foo", "Foo", false);

        postService.savePost(post);

        assertFalse(postService.isPostOwner(post.getId(), UUID.randomUUID()));
    }

    @Test
    void isPostOwner_IfNoPostExist_ThrowsException() {
        assertThrows(HttpStatusException.class, () -> postService.isPostOwner(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void getPostById_IfNoPostExist_ThrowsException() {
        assertThrows(HttpStatusException.class, () -> postService.getPostById(UUID.randomUUID()));
    }

    @Test
    void getPostById_IfPostExist() {
        Post post = new Post("Foo", "Foo", false);

        postService.savePost(post);

        assertEquals(post, postService.getPostById(post.getId()));
    }

    @Test
    void getAllPosts_IfNoPostsExist_ReturnsTrue() {
        assertTrue(postService.getAllPosts().isEmpty());
    }

    @Test
    void getAllPosts_IfSomePostsExist_ReturnsTrue() {
        Post post = new Post("Foo", "Foo", false);

        postService.savePost(post);
        List<Post> posts = postService.getAllPosts();

        assertTrue(posts.size() > 0);
    }

    @Test
    void getAllPosts_IfAllPostsReceived_ReturnsTrue() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Foo1", "Foo1", false));
        posts.add(new Post("Foo2", "Foo2", false));
        posts.add(new Post("Foo3", "Foo3", false));

        posts.forEach(post -> postService.savePost(post));
        List<Post> postsFound = postService.getAllPosts();

        assertEquals(posts, postsFound);
    }

    @Test
    void getAllPosts_IfNoDeletedPostsReceived() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Foo1", "Foo1", false));
        posts.add(new Post("Foo2", "Foo2", false));
        posts.add(new Post("Foo3", "Foo3", false));

        posts.forEach(post -> postService.savePost(post));
        postService.deletePostById(posts.get(0).getId());
        List<Post> postsFound = postService.getAllPosts();

        assertNotEquals(posts.size(), postsFound.size());
    }

    @Test
    void updatePostById_IfPostUpdated() {
        Post expected = new Post("Foo", "Foo", false);

        postService.savePost(expected);

        PostUpdateDTO updateDTO = new PostUpdateDTO(expected.getTitle(), expected.getDescription(), 100, true);
        postMapper.updatePost(expected, updateDTO);
        postService.updatePost(expected);

        Post actual = postService.getPostById(expected.getId());
        expected.setUpdatedAt(actual.getUpdatedAt());

        assertEquals(expected, actual);
    }
}