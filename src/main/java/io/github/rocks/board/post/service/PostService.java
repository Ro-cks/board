package io.github.rocks.board.post.service;

import io.github.rocks.board.post.dto.*;
import io.github.rocks.board.post.entity.Post;
import io.github.rocks.board.post.exception.PostNotFoundException;
import io.github.rocks.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        postRepository.delete(post);
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        post.update(request.title(), request.content());

        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryResponse> findAll(PostSearchType type, String keyword, Pageable pageable) {
        Page<Post> posts;

        if (keyword == null || keyword.isBlank()) {
            posts = postRepository.findAll(pageable);
        } else {
            keyword = keyword.strip();

            posts = switch (type) {
                case TITLE -> postRepository.findByTitleContaining(keyword, pageable);
                case CONTENT -> postRepository.findByContentContaining(keyword, pageable);
                case ALL -> postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
            };
        }

        return posts.map(PostSummaryResponse::from);
    }
}
