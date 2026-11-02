package io.github.rocks.board.post.repository;

import io.github.rocks.board.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
