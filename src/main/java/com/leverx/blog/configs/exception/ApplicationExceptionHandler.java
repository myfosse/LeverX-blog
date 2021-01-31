package com.leverx.blog.configs.exception;

import com.leverx.blog.payload.response.MessageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

/** @author Andrey Egorov */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<?> handleThereIsNoSuchUserException() {
    return new ResponseEntity<>(
        new EntityNotFoundException("There is no such entity"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MessagingException.class)
  protected ResponseEntity<?> handleCantSendEmail() {
    return new ResponseEntity<>(
        new EntityNotFoundException("Can't sent email to this address"), HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    StringBuffer stringBuffer = new StringBuffer();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(fieldError -> stringBuffer.append(fieldError.getDefaultMessage()).append("\n"));

    return new ResponseEntity<>(
        MessageResponse.builder().message(stringBuffer.toString()).build(),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }
}
