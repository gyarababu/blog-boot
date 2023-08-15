package com.blog.boot.exception;

import com.blog.boot.payload.APIErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Class level exception annotation
// Extends ResponseEntityExceptionHandler for Custom Exception
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Method level exception annotation
    // Handling specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    // pass specific exception as parameter
    public ResponseEntity<APIErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest){
        // create a new object by passing parameters
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                // we are only passing the url in details or description, so we write false
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
                // we are only passing the url in details or description, so we write false
                webRequest.getDescription(false));
        return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.BAD_REQUEST);
    }

    // Method level exception annotation
    // Handling specific exceptions
   @ExceptionHandler(AccessDeniedException.class)
   public ResponseEntity<APIErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
                                                                      WebRequest webRequest){

        // creating new object by passing parameters
       APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
               exception.getMessage(),
               // we are only passing the url in details or description, so we write false
               webRequest.getDescription(false));
       return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.UNAUTHORIZED);
   }

    // Global Exception Handler
    // Handling all exceptions in one place
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorDetails> handleGlobalException(Exception exception,
                                                                 WebRequest webRequest){
        // create a new object by passing parameters
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                // we are only passing the url in details or description, so we write false
                webRequest.getDescription(false));
        return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handleMethodArgumentNotValid is one of the methods inside ResponseEntityExceptionHandler
    // paste handleMethodArgumentNotValid and press ctl+space
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        // getting the values using map
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
