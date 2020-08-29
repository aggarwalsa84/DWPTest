package com.app.test.advice;

import com.app.test.dto.ErrorDTO;
import com.app.test.exception.UserNotFoundException;
import com.app.test.exception.UserServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UserServiceErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handle(UserNotFoundException ex) {
        return buildResponseEntity(new ErrorDTO(NOT_FOUND, ex.getMessage(), ex));
    }

    @ExceptionHandler({UserServiceException.class})
    protected ResponseEntity<Object> handle(UserServiceException ex) {
            return buildResponseEntity(new ErrorDTO(INTERNAL_SERVER_ERROR, ex.getMessage(), ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, errorDTO.getStatus());
    }
}
