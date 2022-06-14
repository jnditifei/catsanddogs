package com.domytask.catdog.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(InvalidEntityToPersistException.class)
    public ResponseEntity<ErrorMsgDTO> invalidEntityToPersistException(InvalidEntityToPersistException ex, WebRequest request) {
        ErrorMsgDTO msgDTO = ex.getErrorDto();
        return new ResponseEntity<>(msgDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorMsgDTO> notFoundEntityException(NotAuthorizeActionException ex, WebRequest request) {
        ErrorMsgDTO msgDTO = ex.getErrorDto();
        return new ResponseEntity<>(msgDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizeActionException.class)
    public ResponseEntity<ErrorMsgDTO> notAuthorizeActionException(NotAuthorizeActionException ex, WebRequest request) {
        ErrorMsgDTO message = ex.getErrorDto();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsgDTO> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMsgDTO msgDTO = new ErrorMsgDTO(ex.getMessage());

        return new ResponseEntity<ErrorMsgDTO>(msgDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
