package com.sendi.v1.exception;

import com.sendi.v1.exception.custom.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<Object> handleDuplicateException(DuplicationException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<Object> handleNoSuchObjectException(NoSuchObjectException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /*@ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity<Object> handleUserDuplicateException(UserDuplicationException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorityDuplicationException.class)
    public ResponseEntity<Object> handleAuthorityDuplicationException(AuthorityDuplicationException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleDuplicationException.class)
    public ResponseEntity<Object> handleRoleDuplicationException(RoleDuplicationException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchAuthorityException.class)
    public ResponseEntity<Object> handleNoSuchAuthorityException(NoSuchAuthorityException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchRoleException.class)
    public ResponseEntity<Object> handleNoSuchRoleException(NoSuchRoleException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<Object> handleNoSuchUserException(NoSuchUserException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }*/

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> globalExceptionHandler(Exception exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
