package com.ecommerce.common;

import com.ecommerce.exceptions.DataNotFoundException;
import com.ecommerce.exceptions.ObjectIsNullException;
import com.ecommerce.json.ApiResponse;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleInternalServerException(BindException exception,WebRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorCode("500");
        apiResponse.setException("Internal Server Exception.");

        apiResponse.setErrorMessages(exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList()));
        apiResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        apiResponse.setTimestamp(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse> handleBadRequests(BindException exception,WebRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorCode("400");
        apiResponse.setException("Bad Request");

        apiResponse.setErrorMessages(exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList()));
        apiResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        apiResponse.setTimestamp(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    /*In case we use JPA.*/
    @ExceptionHandler(PersistenceException.class)
    public void persistanceExceptionHandler(PersistenceException persistenceException) {
        System.out.println(persistenceException.getMessage());
        System.out.println("-----persistanceExceptionHandler-------");

    }


    /* START */
    /* This exception handler only occur when the inter-acting with DB. */
    /* ********** need to implement other child exception handler */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> constraintViolationExceptionHandle(WebRequest request) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setErrorCode("400");
        apiResponse.setException("Constraint Violated Exception");
        apiResponse.setErrorMessages(Arrays.asList("Error occurred while executing a statement."));
        apiResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        apiResponse.setTimestamp(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    /*This exception handler only occur when the inter-acting with DB.*/
    /*In case we use hibernate as provider.*/
    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<ApiResponse> hibernateExceptionHandler(HibernateException hibernateException, WebRequest request) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setErrorCode("500");
        apiResponse.setException("Internal Server Error.");
        apiResponse.setErrorMessages(Arrays.asList(hibernateException.getMessage()));
        apiResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        apiResponse.setTimestamp(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli());
        return ResponseEntity.internalServerError().body(apiResponse);
    }
    /* END */



    @ExceptionHandler(ObjectIsNullException.class)
    public ResponseEntity<ApiResponse> handlerRuntime(ObjectIsNullException exception, WebRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorCode("204");
        apiResponse.setException("NO Content");

        apiResponse.setErrorMessages(Arrays.asList("Object is empty."));
        apiResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        apiResponse.setTimestamp(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerRuntime(DataNotFoundException exception, WebRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorCode(exception.getErrorCode());
        apiResponse.setException("DataNotFoundException");

        apiResponse.setErrorMessages(Arrays.asList(exception.getErrorMsg()));
        apiResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        apiResponse.setTimestamp(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC).toEpochMilli());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
