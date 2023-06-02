package com.api.beautynote.services.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushNotificationType {
  NEW_BOOKING_REQUEST(new HashMap<>(Map.of(
      "notificationCode", "new_booking_request",
      "notificationMessage", "You have received a new booking request!")
  )),

  SLOT_STATUS_CHANGE(new HashMap<>(Map.of(
      "notificationCode", "slot_status_change",
      "notificationMessage", "The slot status you are attached to has changed!"
  )));

  private final Map<String, String> content;
}