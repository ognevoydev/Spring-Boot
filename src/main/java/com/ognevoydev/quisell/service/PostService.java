package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.model.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post getPostById(UUID postId);
    List<Post> getAllPosts();
    void savePost(Post post);
    void deletePostById(UUID postId);
    boolean isPostOwner(UUID postId, UUID accountId);
    void updatePost(Post post);

}
