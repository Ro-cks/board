package io.github.rocks.board.post.dto;

import io.github.rocks.board.post.entity.Post;
import java.time.LocalDateTime;

public record PostSummaryResponse(
        Long id,
        String title,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostSummaryResponse from(Post post) {
        return new PostSummaryResponse(
                post.getId(),
                post.getTitle(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}