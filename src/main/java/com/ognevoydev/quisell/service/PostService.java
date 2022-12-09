package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.model.Post;
import java.util.List;
import java.util.UUID;

public interface PostService {

    Post getPostById(UUID postId);
    List<Post> getAllPosts();
    void savePost(Post post);

}
