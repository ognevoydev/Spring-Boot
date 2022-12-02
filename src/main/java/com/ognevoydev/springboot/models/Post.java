package com.ognevoydev.springboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, unique = true)
    UUID id;
    @Column(name="title", nullable = false)
    String title;
    @Column(name="description", nullable = false)
    String description;
    @Column(name="price")
    Integer price;
    @Column(name="used", nullable = false)
    Boolean used;
    @Column(name="phone", nullable = false)
    String phone;
    @Column(name="created_at", nullable = false)
    Timestamp createdAt;
    @Column(name="updated_at")
    Timestamp updatedAt;
    @Column(name="deleted_at")
    Timestamp deletedAt;

}
