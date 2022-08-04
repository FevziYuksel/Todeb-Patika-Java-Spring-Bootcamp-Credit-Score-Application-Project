package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class CustomJwtException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
