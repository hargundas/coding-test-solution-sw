package com.smallworld.exception;

import com.smallworld.util.ExceptionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionLogger exceptionLogger;

    @Autowired
    public GlobalExceptionHandler(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    @ExceptionHandler(FileLoadException.class)
    public ResponseEntity<ExceptionResponse> handleFileLoadException(FileLoadException ex) {
        exceptionLogger.logException("Error while loading file: {}", ex);
        ExceptionResponse response = new ExceptionResponse("Error while loading the file", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> defaultException(Exception e) {
        exceptionLogger.logException("Error occurred: {}", e);
        ExceptionResponse response = new ExceptionResponse("An internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
