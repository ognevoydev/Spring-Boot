package com.ognevoydev.quisell.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
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
    UUID id;
    String title;
    String description;
    Integer price;
    Boolean used;
    String phone;
    @Column(name="created_at", nullable = false)
    Instant createdAt;
    @Column(name="updated_at")
    Instant updatedAt;
    @Column(name="deleted_at")
    Instant deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) && title.equals(post.title) && description.equals(post.description) && Objects.equals(price, post.price) && used.equals(post.used) && phone.equals(post.phone) && createdAt.equals(post.createdAt) && Objects.equals(updatedAt, post.updatedAt) && Objects.equals(deletedAt, post.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, used, phone, createdAt, updatedAt, deletedAt);
    }
}
