package com.example.demowithtests.util;

import com.example.demowithtests.util.exception.*;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Resource not found", request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceIsPrivateException.class)
    protected ResponseEntity<?> handleDeleteException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Resource is private", request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BackEndIsDeadException.class)
    public ResponseEntity<?> handleServerIsDeadException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "BackEnd is dead", request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError f : ex.getFieldErrors()) {
            errors.add(f.getCode() + " " + f.getDefaultMessage());
        }
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                "Validation failed! Details: " + errors,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseBody
    public ResponseEntity<?> handleFileSizeLimitExceededException(WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),
                "File exceeds its max size of 1048576 bytes (1 MB)",
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<?> handleWrongFileFormatException(HttpMediaTypeNotSupportedException error, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(new Date(),
                error.getMessage(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }
}
