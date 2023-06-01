package com.api.beautynote.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for representing all possible authentication
 * and authorization (security) exceptions.
 */
@Getter
public class SlotException extends RuntimeException {

  /**
   * Enum that describes type of exception.
   */
  @AllArgsConstructor
  public enum SlotExceptionProfile {

    SLOT_IN_PAST("slot_in_past",
        "Slot is in past an can not be booked anymore.", HttpStatus.BAD_REQUEST),

    SLOT_IS_OCCUPIED("slot_is_occupied",
        "Slot is not available.", HttpStatus.BAD_REQUEST),

    SLOT_NOT_FOUND("slot_not_found",
        "Slot by given id not found.", HttpStatus.NOT_FOUND);


    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final SlotExceptionProfile slotExceptionProfile;

  public SlotException(SlotExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.slotExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return slotExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return slotExceptionProfile.responseStatus;
  }
}
