package com.ognevoydev.quisell.repository;

import com.ognevoydev.quisell.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
