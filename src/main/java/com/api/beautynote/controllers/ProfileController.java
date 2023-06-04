package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.requests.EditSlotRequestBody;
import com.api.beautynote.controllers.dto.requests.MasterServiceRequestDto;
import com.api.beautynote.controllers.dto.requests.RegistrationRequestDto;
import com.api.beautynote.controllers.dto.requests.SlotRequestDto;
import com.api.beautynote.controllers.dto.responses.ClientProfileDto;
import com.api.beautynote.controllers.dto.responses.MasterProfileDto;
import com.api.beautynote.controllers.dto.responses.MasterServiceArrayDto;
import com.api.beautynote.controllers.dto.responses.MasterServiceDto;
import com.api.beautynote.controllers.dto.responses.PublicSlotsMapDto;
import com.api.beautynote.controllers.dto.responses.SlotDto;
import com.api.beautynote.controllers.dto.responses.SlotsMapDto;
import com.api.beautynote.exceptions.MasterServiceException;
import com.api.beautynote.exceptions.MasterServiceException.MasterServiceExceptionProfile;
import com.api.beautynote.exceptions.ServiceException;
import com.api.beautynote.exceptions.ServiceException.ServiceExceptionProfile;
import com.api.beautynote.exceptions.SlotException;
import com.api.beautynote.exceptions.SlotException.SlotExceptionProfile;
import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.master.MasterService;
import com.api.beautynote.model.master_service.MasterServiceKey;
import com.api.beautynote.model.master_service.MasterServiceService;
import com.api.beautynote.model.service.Service;
import com.api.beautynote.model.service.ServiceService;
import com.api.beautynote.model.slot.Slot;
import com.api.beautynote.model.slot.SlotService;
import com.api.beautynote.model.slot.SlotStatus;
import com.api.beautynote.model.user.Role;
import com.api.beautynote.model.user.User;
import com.api.beautynote.model.user.UserService;
import com.api.beautynote.security.JwtUserDetails;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ProfileController {

  private final UserService userService;
  private final MasterService masterService;
  private final SlotService slotService;
  private final MasterServiceService masterServiceService;
  private final ServiceService serviceService;

  @Autowired
  public ProfileController(UserService userService, MasterService masterService,
      SlotService slotService, MasterServiceService masterServiceService,
      ServiceService serviceService) {
    this.userService = userService;
    this.masterService = masterService;
    this.slotService = slotService;
    this.masterServiceService = masterServiceService;
    this.serviceService = serviceService;
  }

  @GetMapping("/me")
  public ResponseEntity<MasterProfileDto> getSelfProfile(Authentication authentication) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    MasterProfileDto profileDto = new MasterProfileDto(user);

    if (user.getRole() == Role.MASTER) {
      profileDto.setPhoneNumber(user.getPhoneNumber());
      profileDto.setEmail(user.getEmail());
    }

    return ResponseEntity.ok(new MasterProfileDto(user));
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

  @GetMapping("/me/slots")
  public ResponseEntity<SlotsMapDto> getAllRelatedSlots(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startDateTime,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endDateTime,
      @RequestParam(required = false) String status,
      Authentication authentication
  ) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    List<Slot> userSlots = (user.getRole() == Role.MASTER) ?
        slotService.findByMaster(user.getMaster()) :
        slotService.findByClient(user);

    userSlots = userSlots.stream()
        .filter(x -> (Objects.isNull(startDateTime)) || x.getFrom().toInstant().atZone(ZoneId.systemDefault()).isAfter(startDateTime))
        .filter(x -> (Objects.isNull(endDateTime)) || x.getTo().toInstant().atZone(ZoneId.systemDefault()).isBefore(endDateTime))
        .filter(x -> (Objects.isNull(status) || x.getStatus() == SlotStatus.valueOf(status)))
        .collect(Collectors.toList());

    return ResponseEntity.ok(new SlotsMapDto(userSlots));
  }

  @PreAuthorize("hasRole('MASTER')")
  @PostMapping("/me/slots")
  public ResponseEntity<SlotDto> addNewSlot(
      @RequestBody SlotRequestDto slotRequestDto,
      Authentication authentication
  ) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    Master master = userService.findByEmail(jwtUserDetails.getEmail()).getMaster();

    // TODO: slot time validation

    Slot slot = new Slot();
    slot.setMaster(master);
    slot.setFrom(Date.from(slotRequestDto.getFrom().toInstant()));
    slot.setTo(Date.from(slotRequestDto.getTo().toInstant()));
    slot.setStatus(SlotStatus.AVAILABLE);

    slot = slotService.save(slot);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new SlotDto(slot));
  }

  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/me/slots/{slotId}")
  public ResponseEntity<SlotDto> editSlot(
      @PathVariable Long slotId,
      @RequestBody EditSlotRequestBody editSlotRequestBody,
      Authentication authentication
  ) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    Master master = userService.findByEmail(jwtUserDetails.getEmail()).getMaster();

    Slot slot = slotService.findById(slotId)
        .orElseThrow(() -> new SlotException(SlotExceptionProfile.SLOT_NOT_FOUND));

    if (slot.getMaster() != master) {
      throw new SlotException(SlotExceptionProfile.SLOT_NOT_FOUND);
    }

    if (slot.getStatus() == SlotStatus.PENDING || slot.getStatus() == SlotStatus.BOOKED) {
      // TODO: send push to client
    }

    slot.setStatus(editSlotRequestBody.getStatus());

    if (slot.getStatus() != SlotStatus.PENDING && slot.getStatus() != SlotStatus.BOOKED) {
      slot.setUser(null);
    }

//    slot.setFrom(Date.from(editSlotRequestBody.getFrom().toInstant()));
//    slot.setTo(Date.from(editSlotRequestBody.getTo().toInstant()));

    slot = slotService.save(slot);

    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new SlotDto(slot));
  }

  @PreAuthorize("hasRole('MASTER')")
  @GetMapping("/me/services")
  public MasterServiceArrayDto getAllMasterServices(
      Authentication authentication
  ) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    List<com.api.beautynote.model.master_service.MasterService> masterServices = masterServiceService.findByMaster(user.getMaster());
    return new MasterServiceArrayDto(masterServices);
  }

  @PreAuthorize("hasRole('MASTER')")
  @PostMapping("/me/services")
  public ResponseEntity<MasterServiceDto> addNewService(
      @RequestBody MasterServiceRequestDto masterServiceRequestDto,
      Authentication authentication
  ) {

    Service service = serviceService.findById(masterServiceRequestDto.getServiceId())
        .orElseThrow(() -> new ServiceException(ServiceExceptionProfile.SERVICE_NOT_FOUND));

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    Master master = userService.findByEmail(jwtUserDetails.getEmail()).getMaster();

    com.api.beautynote.model.master_service.MasterService masterServiceExisting =
        masterServiceService.findById(new MasterServiceKey(master.getId(), service.getId())).orElse(null);

    if (Objects.nonNull(masterServiceExisting)) {
      throw new MasterServiceException(MasterServiceExceptionProfile.SERVICE_ALREADY_DEFINED);
    }

    com.api.beautynote.model.master_service.MasterService newMasterService = new com.api.beautynote.model.master_service.MasterService();
    newMasterService.setService(service);
    newMasterService.setMaster(master);
    newMasterService.setPrice(masterServiceRequestDto.getPrice());
    newMasterService.setAverageTime(masterServiceRequestDto.getAverageTime());
    newMasterService.setDescription(masterServiceRequestDto.getDescription());

    newMasterService = masterServiceService.save(newMasterService);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new MasterServiceDto(newMasterService));
  }

  /**
   * 	/me/slots/{slotId}/review
   *
   * 	/me/reviews/{reviewId}
   *
   *  /me/slots/generate
   * 	/me/settings
   * 	/me/settings/sgps
   * 	/me/settings/sgps/{sgpId}
   * 	/me/settings/sgps/{sgpId}/rules
   * 	/me/settings/sgps/rules/{ruleId}
   */
}
