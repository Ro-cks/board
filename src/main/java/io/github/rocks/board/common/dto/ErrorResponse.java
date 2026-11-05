package io.github.rocks.board.common.dto;

public record ErrorResponse (
        int status, String message
) {

}
