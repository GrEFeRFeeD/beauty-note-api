package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.user.User;
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
  private byte[] image;

  public ClientProfileDto(User user) {
    this(user.getId(),
        user.getEmail(),
        user.getName(),
        user.getSurname(),
        user.getPhoneNumber(),
        user.getCountry(),
        user.getRegion(),
        user.getCity(),
        user.getRole().toString(),
        user.getImage().getContent());
  }
}
