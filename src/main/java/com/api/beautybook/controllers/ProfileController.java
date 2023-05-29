package com.api.beautybook.controllers;

import com.api.beautybook.controllers.dto.responses.ClientProfileDto;
import com.api.beautybook.model.user.User;
import com.api.beautybook.model.user.UserService;
import com.api.beautybook.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private UserService userService;

  @Autowired
  public ProfileController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ClientProfileDto getSelfProfile(Authentication authentication) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());
    ClientProfileDto clientProfileDto = new ClientProfileDto(user);

    return clientProfileDto;
  }

  @PostMapping("/me/role")
  public ClientProfileDto setTheRole(Authentication authentication) {
    return new ClientProfileDto();
  }

  @PostMapping("/me")
  public ClientProfileDto endRegistration(Authentication authentication) {
    return new ClientProfileDto();
  }

  /**
   * 	/me/slots
   * 	/me/slots/generate
   * 	/me/slots/{slotId}
   * 	/me/slots/{slotId}/review
   *
   * 	/me/reviews/{reviewId}
   * 	/me/services
   * 	/me/services/{serviceId}
   *
   * 	/me/settings
   * 	/me/settings/sgps
   * 	/me/settings/sgps/{sgpId}
   * 	/me/settings/sgps/{sgpId}/rules
   * 	/me/settings/sgps/rules/{ruleId}
   */
}
