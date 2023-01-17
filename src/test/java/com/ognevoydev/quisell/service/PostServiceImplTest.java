package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.dto.PostUpdateDTO;
import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.model.mapper.PostMapper;
import com.ognevoydev.quisell.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

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

    @Test
    void deletePostById() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertThrows(HttpStatusException.class, () -> postService.deletePostById(UUID.randomUUID()));

        postService.deletePostById(post.getId());

        assertNotNull(postService.getPostById(post.getId()).getDeletedAt());
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

    @Test
    void updatePostById() {
        Post expected = new Post();
        expected.setTitle("Foo");
        expected.setDescription("Foo");
        expected.setPrice(200);
        expected.setUsed(false);

        postService.savePost(expected);

        PostUpdateDTO updateDTO = new PostUpdateDTO(expected.getTitle(), expected.getDescription(), 100, true);
        postMapper.updatePost(expected, updateDTO);
        postService.updatePost(expected);

        Post actual = postService.getPostById(expected.getId());
        expected.setUpdatedAt(actual.getUpdatedAt());

        assertEquals(expected, actual);
    }

    @Test
    void getPostById() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);
        Post found = postService.getPostById(post.getId());

        assertThrows(HttpStatusException.class, () -> postService.getPostById(UUID.randomUUID()));
        assertNotNull(postService.getPostById(post.getId()));
        assertEquals(post, found);
    }

    @Test
        void getAllPosts() {
        assertFalse(postService.getAllPosts().isEmpty());

        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);
        List<Post> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertTrue(posts.contains(post));
        assertTrue(posts.stream().allMatch(p -> p.getDeletedAt() == null));
    }

    @Test
    void savePost() {
        Post post = new Post();
        post.setTitle("Foo");
        post.setDescription("Foo");
        post.setUsed(false);

        postService.savePost(post);

        assertEquals(post, postService.getPostById(post.getId()));
    }
}