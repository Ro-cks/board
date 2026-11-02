package io.github.rocks.board.post.service;

import io.github.rocks.board.post.dto.PostCreateRequest;
import io.github.rocks.board.post.dto.PostResponse;
import io.github.rocks.board.post.entity.Post;
import io.github.rocks.board.post.exception.PostNotFoundException;
import io.github.rocks.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        Post savedPost = postRepository.save(request.toEntity());

        return PostResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return PostResponse.from(post);
    }
}
