package com.ognevoydev.quisell.service;

import com.ognevoydev.quisell.exception.NotFoundException;
import com.ognevoydev.quisell.model.Post;
import com.ognevoydev.quisell.repo.PostRepository;
import com.ognevoydev.quisell.utils.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public Post getPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format(
                                NotFoundException.MESSAGE_TEMPLATE,
                                TextUtils.getEntityName(Post.class), postId)));
    }

}
