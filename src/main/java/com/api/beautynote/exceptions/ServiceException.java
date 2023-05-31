package com.api.beautynote.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for representing all possible authentication
 * and authorization (security) exceptions.
 */
@Getter
public class ServiceException extends RuntimeException {

  /**
   * Enum that describes type of exception.
   */
  @AllArgsConstructor
  public enum ServiceExceptionProfile {

    SERVICE_NOT_FOUND("service_not_found",
        "Service by given id not found.", HttpStatus.NOT_FOUND);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final ServiceExceptionProfile serviceExceptionProfile;

  public ServiceException(ServiceExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.serviceExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return serviceExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return serviceExceptionProfile.responseStatus;
  }
}
