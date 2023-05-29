package com.api.beautybook.controllers.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfileDto {

  private Long id;
  private String email;
  private String name;
  private String surname;
  private String phoneNumber;
  private String country;
  private String region;
  private String city;
  private String role;
}
