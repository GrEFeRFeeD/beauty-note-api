package com.api.beautynote.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for representing all possible authentication
 * and authorization (security) exceptions.
 */
@Getter
public class MasterException extends RuntimeException {

  /**
   * Enum that describes type of exception.
   */
  @AllArgsConstructor
  public enum MasterExceptionProfile {

    MASTER_NOT_FOUND("not_found",
        "Master by given id not found.", HttpStatus.NOT_FOUND);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final MasterExceptionProfile masterExceptionProfile;

  public MasterException(MasterExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.masterExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return masterExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return masterExceptionProfile.responseStatus;
  }
}
