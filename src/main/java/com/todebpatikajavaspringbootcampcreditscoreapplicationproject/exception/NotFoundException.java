package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception;


import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private String details;

    public NotFoundException(String entityName, String nationalId, String cause ) {
        super(String.format("Related %s by national ID is %s not found with : [%s]",entityName,nationalId,cause) );
        details = super.getLocalizedMessage();
    }
    public NotFoundException(String entityName, String cause) {
        super(String.format("Related %s is not found with : [%s]",entityName,cause));
        details = super.getLocalizedMessage();
    }

}
