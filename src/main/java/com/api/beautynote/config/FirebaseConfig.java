package com.api.beautynote.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfig {

  private final FirebaseProperties firebaseProperties;

  @Autowired
  public FirebaseConfig(FirebaseProperties firebaseProperties) {
    this.firebaseProperties = firebaseProperties;
  }

  @Bean
  GoogleCredentials googleCredentials() {
    try {
      if (firebaseProperties.getServiceAccount() != null) {
        try (InputStream is = firebaseProperties.getServiceAccount().getInputStream()) {
          return GoogleCredentials.fromStream(is);
        }
      }
      return GoogleCredentials.getApplicationDefault();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  FirebaseApp firebaseApp(GoogleCredentials googleCredentials) {
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(googleCredentials)
        .build();

    return FirebaseApp.initializeApp(options);
  }

  @Bean
  FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
    return FirebaseMessaging.getInstance(firebaseApp);
  }
}