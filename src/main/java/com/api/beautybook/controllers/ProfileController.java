package com.api.beautybook.controllers;

import com.api.beautybook.controllers.dto.requests.RegistrationRequestDto;
import com.api.beautybook.controllers.dto.responses.ClientProfileDto;
import com.api.beautybook.controllers.dto.responses.MasterProfileDto;
import com.api.beautybook.model.master.Master;
import com.api.beautybook.model.master.MasterService;
import com.api.beautybook.model.user.Role;
import com.api.beautybook.model.user.User;
import com.api.beautybook.model.user.UserService;
import com.api.beautybook.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private final UserService userService;
  private final MasterService masterService;

  @Autowired
  public ProfileController(UserService userService, MasterService masterService) {
    this.userService = userService;
    this.masterService = masterService;
  }

  @GetMapping("/me")
  public ClientProfileDto getSelfProfile(Authentication authentication) {

    //TODO: for master to
    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());
    ClientProfileDto clientProfileDto = new ClientProfileDto(user);

    return clientProfileDto;
  }

  @PreAuthorize("hasRole('IN_DECIDE')")
  @PostMapping("/me")
  public ResponseEntity<?> endRegistration(
      @RequestBody RegistrationRequestDto registrationRequestDto,
      Authentication authentication
  ) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    user.setName(registrationRequestDto.getName());
    user.setSurname(registrationRequestDto.getSurname());
    user.setCountry(registrationRequestDto.getCountry());
    user.setRegion(registrationRequestDto.getRegion());
    user.setCity(registrationRequestDto.getCity());
    user.setPhoneNumber(registrationRequestDto.getPhoneNumber());
    user.setRole(Role.valueOf(registrationRequestDto.getRole()));

    user = userService.save(user);

    if (user.getRole() == Role.CLIENT) {
      return ResponseEntity.ok(new ClientProfileDto(user));
    }

    Master master = new Master();
    master.setUser(user);
    master.setAddress(registrationRequestDto.getAddress());
    master = masterService.save(master);

    return ResponseEntity.ok(new MasterProfileDto(master));
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
