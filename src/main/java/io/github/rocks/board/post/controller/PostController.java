package io.github.rocks.board.post.controller;

import io.github.rocks.board.common.dto.PageResponse;
import io.github.rocks.board.post.dto.PostCreateRequest;
import io.github.rocks.board.post.dto.PostResponse;
import io.github.rocks.board.post.dto.PostSummaryResponse;
import io.github.rocks.board.post.dto.PostUpdateRequest;
import io.github.rocks.board.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request) {
        PostResponse response = postService.create(request);
        URI location = URI.create("/api/posts/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        postService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PostSummaryResponse>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PostSummaryResponse> page = postService.findAll(pageable);
        PageResponse<PostSummaryResponse> response = PageResponse.from(page);
        return ResponseEntity.ok(response);
    }
}
