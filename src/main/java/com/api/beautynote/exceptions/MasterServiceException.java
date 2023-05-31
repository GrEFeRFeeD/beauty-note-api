package com.api.beautynote.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for representing all possible authentication
 * and authorization (security) exceptions.
 */
@Getter
public class MasterServiceException extends RuntimeException {

  /**
   * Enum that describes type of exception.
   */
  @AllArgsConstructor
  public enum MasterServiceExceptionProfile {

    MASTER_SERVICE_NOT_FOUND("master_service_not_found",
        "Master service by given id not found.", HttpStatus.NOT_FOUND);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final MasterServiceExceptionProfile masterServiceExceptionProfile;

  public MasterServiceException(MasterServiceExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.masterServiceExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return masterServiceExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return masterServiceExceptionProfile.responseStatus;
  }
}
