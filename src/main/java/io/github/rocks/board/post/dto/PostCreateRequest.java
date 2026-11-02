package io.github.rocks.board.post.dto;

import io.github.rocks.board.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotBlank @Size(max = 100) String title,
        @NotBlank String content,
        @NotBlank @Size(max = 50) String author
) {
    public Post toEntity() {
        return new Post(title, content, author);
    }
}