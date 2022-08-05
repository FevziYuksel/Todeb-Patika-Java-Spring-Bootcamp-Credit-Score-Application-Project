package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception;


import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Data
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {

    private String details;

    public AlreadyExistsException(String entityName, String nationalId, String cause) {
        super(String.format("Related %s by national ID %s already exists with : [%s]",entityName,nationalId,cause) );
        details = super.getLocalizedMessage();

    }
    public AlreadyExistsException(String entityName, String cause) {
        super(String.format("Related %s already exists with : [%s]",entityName,cause));
        details = super.getLocalizedMessage();
    }
}
