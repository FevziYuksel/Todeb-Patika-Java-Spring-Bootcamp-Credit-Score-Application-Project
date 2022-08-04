package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.handler;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.CustomJwtException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {



    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception exception) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseMap);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExistsException(AlreadyExistsException exception){
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", exception.getMessage());
        errorResponseMap.put("error_details", exception.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponseMap);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException exception) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", exception.getMessage());
        errorResponseMap.put("error_details", exception.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseMap);
    }

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<Map<String, String>> handleCustomJwtException(CustomJwtException exception) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", exception.getMessage());
        errorResponseMap.put("error_status", exception.getHttpStatus().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseMap);
    }

}
