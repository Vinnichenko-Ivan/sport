package com.hits.sport.filter;


import com.hits.sport.exception.CustomException;
import com.hits.sport.exception.MessageDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

import java.io.IOException;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<MessageDto> handleIllegalArgument(CustomException ex) throws IOException {
        MessageDto messageDto = new MessageDto(ex.getMessage());
        return new ResponseEntity<MessageDto>(messageDto, ex.getStatus());
    }


    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<MessageDto> handleValidationException(ValidationException ex) throws IOException {
        MessageDto messageDto = new MessageDto("bad request");
        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        MessageDto messageDto = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
    }



}