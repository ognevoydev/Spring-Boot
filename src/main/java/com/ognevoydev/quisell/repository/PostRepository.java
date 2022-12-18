package com.ognevoydev.quisell.repository;

import com.ognevoydev.quisell.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findActivePostsByDeletedAtIsNull();
@Query(value = "SELECT accountId = :accountId FROM Post where id = :postId")
    Optional<Boolean> isPostOwner(UUID postId, UUID accountId);
    @Modifying
    @Query(value = "UPDATE Post SET deletedAt = :deletedAt where id = :postId")
    int setDeletedAt(Instant deletedAt, UUID postId);
}
