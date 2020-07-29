package com.ron.springboot_api.handler;

import com.ron.springboot_api.exception.InvalidRequestException;
import com.ron.springboot_api.exception.NotFoundException;
import com.ron.springboot_api.resource.ErrorResource;
import com.ron.springboot_api.resource.FieldResource;
import com.ron.springboot_api.resource.InvalidErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(RuntimeException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        return new ResponseEntity<Object>(errorResource, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e) {
       Errors errors = e.getErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldResource fieldResource = new FieldResource(fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage());

            fieldResources.add(fieldResource);
        }
       InvalidErrorResource ier = new InvalidErrorResource(e.getMessage(),fieldResources );
        return new ResponseEntity<Object>(ier, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
