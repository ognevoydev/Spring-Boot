package com.ognevoydev.quisell.repository;

import com.ognevoydev.quisell.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findActivePostsByDeletedAtIsNull();
    @Query(value = """
            SELECT CAST(CASE
            WHEN (SELECT accountId FROM Post where id = :postId) = :principal
            THEN 1 ELSE 0 END AS numeric_boolean) AS isAuthenticated
            """)
    boolean checkAccessToPost(@Param("postId") UUID postId, @Param("principal") Principal principal);
    @Modifying
    @Query(value = "UPDATE Post SET deletedAt = :deletedAt where id = :postId")
    int updPostDeletedAt(@Param("deletedAt") Instant deletedAt, @Param("postId") UUID postId);

}
