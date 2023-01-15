package com.ognevoydev.quisell.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue()
    private UUID id;
    @Column(name="account_id")
    private UUID accountId;
    private String title;
    private String description;
    private Integer price;
    private Boolean used;
    @CreationTimestamp
    @Column(name="created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updatedAt;
    @Column(name="deleted_at")
    private Date deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) && title.equals(post.title) && description.equals(post.description) && Objects.equals(price, post.price) && used.equals(post.used) && createdAt.equals(post.createdAt) && Objects.equals(updatedAt, post.updatedAt) && Objects.equals(deletedAt, post.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, used, createdAt, updatedAt, deletedAt);
    }
}
