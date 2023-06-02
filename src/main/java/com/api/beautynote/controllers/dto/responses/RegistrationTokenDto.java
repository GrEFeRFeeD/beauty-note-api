package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationTokenDto {

  private Long userId;
  private Role role;
  private String registrationToken;
}
