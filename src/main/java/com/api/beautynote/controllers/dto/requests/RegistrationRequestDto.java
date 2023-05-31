package com.api.beautynote.controllers.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

  private String role;
  private String name;
  private String surname;
  private String phoneNumber;
  private String country;
  private String region;
  private String city;
  private String address;
}
