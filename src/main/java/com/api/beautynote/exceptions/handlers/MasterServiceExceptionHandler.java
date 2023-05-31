package com.api.beautynote.exceptions.handlers;

import com.api.beautynote.exceptions.MasterException;
import com.api.beautynote.exceptions.MasterServiceException;
import com.api.beautynote.exceptions.MasterServiceException.MasterServiceExceptionProfile;
import com.api.beautynote.exceptions.handlers.dto.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler for security exceptions.
 * Handles authentication and authorization exceptions.
 */
@RestControllerAdvice
public class MasterServiceExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Method for handling SecurityException.
   */
  @ExceptionHandler(value = MasterServiceException.class)
  public ResponseEntity<Object> handleSecurityException(MasterServiceException exception,
      WebRequest webRequest) {

    var exceptionBody = new ExceptionDto(exception.getName(), exception.getMessage());

    return handleExceptionInternal(exception, exceptionBody, new HttpHeaders(),
        exception.getResponseStatus(), webRequest);
  }
}
