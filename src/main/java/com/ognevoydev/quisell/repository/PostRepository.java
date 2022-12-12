package com.ognevoydev.quisell.repository;

import com.ognevoydev.quisell.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByDeletedAtIsNull();
    @Query(value = "SELECT account_id FROM posts where id = ?1", nativeQuery = true)
    UUID findAccountIdById(UUID postId);
    @Modifying
    @Query(value = "UPDATE posts SET deleted_at = ?1 where id = ?2", nativeQuery = true)
    int updPostDeletedAt(Instant deletedAt, UUID postId);

}
