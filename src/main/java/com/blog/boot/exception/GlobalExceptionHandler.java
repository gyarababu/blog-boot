package com.blog.boot.exception;

import com.blog.boot.payload.APIErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

// Class level exception annotation
@ControllerAdvice
public class GlobalExceptionHandler {

    // Method level exception annotation
    // Handling specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    // pass specific exception as parameter
    public ResponseEntity<APIErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest){
        // create a new object by passing parameters
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.NOT_FOUND);
    }

    // Method level exception annotation
    // Handling specific exceptions
    @ExceptionHandler(BlogAPIException.class)
    // pass specific exception as parameter
    public ResponseEntity<APIErrorDetails> handleBlogAPIException(BlogAPIException exception,
                                                                           WebRequest webRequest){
        // create a new object by passing parameters
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.BAD_REQUEST);
    }

}
