package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.requests.RegistrationRequestDto;
import com.api.beautynote.controllers.dto.responses.ClientProfileDto;
import com.api.beautynote.controllers.dto.responses.MasterProfileDto;
import com.api.beautynote.controllers.dto.responses.PublicSlotsMapDto;
import com.api.beautynote.controllers.dto.responses.SlotDto;
import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.master.MasterService;
import com.api.beautynote.model.slot.Slot;
import com.api.beautynote.model.slot.SlotService;
import com.api.beautynote.model.slot.SlotStatus;
import com.api.beautynote.model.user.Role;
import com.api.beautynote.model.user.User;
import com.api.beautynote.model.user.UserService;
import com.api.beautynote.security.JwtUserDetails;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private final UserService userService;
  private final MasterService masterService;
  private final SlotService slotService;

  @Autowired
  public ProfileController(UserService userService, MasterService masterService,
      SlotService slotService) {
    this.userService = userService;
    this.masterService = masterService;
    this.slotService = slotService;
  }

  @GetMapping("/me")
  public ResponseEntity<?> getSelfProfile(Authentication authentication) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    if (user.getRole() == Role.MASTER) {
      MasterProfileDto masterProfileDto = new MasterProfileDto(user.getMaster());
      masterProfileDto.setPhoneNumber(user.getPhoneNumber());
      masterProfileDto.setEmail(user.getEmail());
      return ResponseEntity.ok(masterProfileDto);
    }

    return ResponseEntity.ok(new ClientProfileDto(user));
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

  @PreAuthorize("hasRole('CLIENT')")
  @GetMapping("/me/slots")
  public PublicSlotsMapDto getAllClientSlots(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startDateTime,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endDateTime,
      @RequestParam(required = false) String status,
      Authentication authentication
  ) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User client = userService.findByEmail(jwtUserDetails.getEmail());

    List<Slot> userSlots = slotService.findByClient(client).stream()
        .filter(x -> (Objects.isNull(startDateTime)) || x.getFrom().toInstant().atZone(ZoneId.systemDefault()).isAfter(startDateTime))
        .filter(x -> (Objects.isNull(endDateTime)) || x.getTo().toInstant().atZone(ZoneId.systemDefault()).isBefore(endDateTime))
        .filter(x -> (Objects.isNull(status) || x.getStatus() == SlotStatus.valueOf(status)))
        .collect(Collectors.toList());

    return new PublicSlotsMapDto(userSlots);
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
