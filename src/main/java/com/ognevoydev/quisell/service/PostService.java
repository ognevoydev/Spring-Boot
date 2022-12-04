package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.model.Post;
import java.util.UUID;

public interface PostService {

    Post getPostById(UUID postId);

}
