package com.api.beautynote.services;

import com.api.beautynote.model.slot.Slot;
import com.api.beautynote.model.user.User;
import com.api.beautynote.services.dto.PushNotificationType;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private final FirebaseMessaging firebaseMessaging;

  @Autowired
  public NotificationService(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }

  public void sendPushNotification(User toUser, PushNotificationType notificationType, Slot slot) {

    Message message = Message.builder()
        .setToken(toUser.getRegistrationToken())
        .putAllData(notificationType.getContent())
        .putData("slotId", slot.getId().toString())
        .build();

    try {
      String messageIdentifier = firebaseMessaging.send(message);
    } catch (FirebaseMessagingException e) {
      System.out.println("Error occurred while pushing message for user by id " + toUser.getId() + " , slot by id" + slot.getId());
    }

  }
}
