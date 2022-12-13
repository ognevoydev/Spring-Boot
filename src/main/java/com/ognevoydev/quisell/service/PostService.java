package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.model.Post;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {

    Post getPostById(UUID postId);
    List<Post> getAllPosts();
    void savePost(Post post);
    void deletePostById(UUID postId);
    boolean checkAccessToPost(UUID postId, Principal principal);

}
