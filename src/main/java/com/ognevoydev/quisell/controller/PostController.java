package com.ognevoydev.quisell.controller;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.model.dto.PostUpdateDTO;
import com.ognevoydev.quisell.model.entity.Post;
import com.ognevoydev.quisell.model.mapper.PostMapper;
import com.ognevoydev.quisell.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
@Tag(name = "Объявления")
public class PostController {

    private final PostService postService;

    private final PostMapper postMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Получить объявление по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post with id {id} was found"), @ApiResponse(responseCode = "404", description = "Post with id {id} not found")})
    public Post getPostById(
            @Parameter(description = "ID объявления")
            @PathVariable(value = "id") UUID postId) {
        return postService.getPostById(postId);
    }

    @GetMapping
    @Operation(summary = "Получить список всех активных объявлений")
    @ApiResponse(responseCode = "200", description = "List of posts was found")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    @Operation(summary = "Создать объявление")
    @ApiResponse(responseCode = "200", description = "Post was created")
    public void addPost(@RequestBody Post post) {
        postService.savePost(post);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объявление по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post with id {id} was deleted"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Post with id {id} not found")})
    public void deletePost(
            @Parameter(description = "ID объявления")
            @PathVariable(value = "id") UUID postId, Principal principal) {

        if(postService.isPostOwner(postId, UUID.fromString(String.valueOf(principal)))) {
            postService.deletePostById(postId);
        }
        else {
            throw new HttpStatusException("Access to this resource on the server is denied", FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить объявление по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post with id {id} was updated"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Post with id {id} not found")})
    public void updatePost(
            @Parameter(description = "ID объявления")
            @PathVariable(value = "id") UUID postId,
            @RequestBody PostUpdateDTO updateDTO,
            Principal principal) {
        if(postService.isPostOwner(postId, UUID.fromString(String.valueOf(principal)))) {
            Post post = postService.getPostById(postId);
            postMapper.updatePost(post, updateDTO);
            postService.updatePost(post);
        }
        else {
            throw new HttpStatusException("Access to this resource on the server is denied", FORBIDDEN);
        }
    }

}
