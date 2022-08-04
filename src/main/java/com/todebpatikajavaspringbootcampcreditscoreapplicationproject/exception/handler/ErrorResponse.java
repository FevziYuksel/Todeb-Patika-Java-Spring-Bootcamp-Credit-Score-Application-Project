package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.handler;

import java.util.List;

public class ErrorResponse {

    private String message;
    private List<String> details;


    public ErrorResponse(String message, List<String> details) {
        super();
        this.details = details;
        this.message = message;
    }
}
