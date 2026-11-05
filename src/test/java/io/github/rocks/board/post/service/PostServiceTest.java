package io.github.rocks.board.post.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import io.github.rocks.board.post.exception.PostNotFoundException;
import io.github.rocks.board.post.repository.PostRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void findById_throwsException_whenPostDoesNotExist() {
        // given: 999번 게시글이 없는 상황
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then: 조회하면 예외 발생
        assertThatThrownBy(() -> postService.findById(999L))
                .isInstanceOf(PostNotFoundException.class);
    }
}