package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.requests.BookingRequestDto;
import com.api.beautynote.controllers.dto.responses.MasterArrayDto;
import com.api.beautynote.controllers.dto.responses.MasterServiceArrayDto;
import com.api.beautynote.controllers.dto.responses.PublicMasterProfileDto;
import com.api.beautynote.controllers.dto.responses.PublicSlotDto;
import com.api.beautynote.controllers.dto.responses.PublicSlotsMapDto;
import com.api.beautynote.exceptions.MasterException;
import com.api.beautynote.exceptions.MasterException.MasterExceptionProfile;
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
import com.api.beautynote.model.master_type.MasterType;
import com.api.beautynote.model.master_type.MasterTypeService;
import com.api.beautynote.model.service.Service;
import com.api.beautynote.model.service.ServiceService;
import com.api.beautynote.model.slot.Slot;
import com.api.beautynote.model.slot.SlotService;
import com.api.beautynote.model.slot.SlotStatus;
import com.api.beautynote.model.user.User;
import com.api.beautynote.model.user.UserService;
import com.api.beautynote.security.JwtUserDetails;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterController {

  private final MasterService masterService;
  private final MasterTypeService masterTypeService;
  private final ServiceService serviceService;
  private final MasterServiceService masterServiceService;
  private final SlotService slotService;
  private UserService userService;

  @Autowired
  public MasterController(MasterService masterService, MasterTypeService masterTypeService,
      ServiceService serviceService, MasterServiceService masterServiceService,
      SlotService slotService, UserService userService) {
    this.masterService = masterService;
    this.masterTypeService = masterTypeService;
    this.serviceService = serviceService;
    this.masterServiceService = masterServiceService;
    this.slotService = slotService;
    this.userService = userService;
  }

  @GetMapping("/masters")
  public MasterArrayDto getAllMasters(
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String region,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) List<Long> types,
      @RequestParam(required = false) List<Long> services
  ) {

    types = (Objects.isNull(types)) ? new ArrayList<>() : types;
    services = (Objects.isNull(services)) ? new ArrayList<>() : services;

    List<MasterType> masterTypes = types.stream()
        .map(masterTypeService::findOptionallyById)
        .map(x -> x.orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<Service> serviceList = services.stream()
        .map(serviceService::findOptionallyById)
        .map(x -> x.orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<Master> masters = masterService.findAll().stream()
        .filter(x -> (Objects.isNull(country) || x.getUser().getCountry().equals(country)))
        .filter(x -> (Objects.isNull(region) || x.getUser().getRegion().equals(region)))
        .filter(x -> (Objects.isNull(city) || x.getUser().getCity().equals(city)))
        .filter(x -> (masterTypes.isEmpty() || containsAtLeastOne(masterTypes, x.getMasterTypes())))
        .filter(x -> (serviceList.isEmpty() || containsAtLeastOne(serviceList,
            masterServiceService.findByMaster(x).stream()
                .map(com.api.beautynote.model.master_service.MasterService::getService)
                .collect(Collectors.toList())
        )))
        .collect(Collectors.toList());

    return new MasterArrayDto(masters);
  }

  private static <T> boolean containsAtLeastOne(Collection<T> neededElements,
      Collection<T> actualElements) {
    for (T item : neededElements) {
      if (actualElements.contains(item)) {
        return true;
      }
    }
    return false;
  }

  @GetMapping("/masters/services")
  public MasterServiceArrayDto getAllMasterServices(
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String region,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) List<Long> types,
      @RequestParam(required = false) List<Long> services
  ) {

    types = (Objects.isNull(types)) ? new ArrayList<>() : types;
    services = (Objects.isNull(services)) ? new ArrayList<>() : services;

    List<MasterType> masterTypes = types.stream()
        .map(masterTypeService::findOptionallyById)
        .map(x -> x.orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<Service> serviceList = services.stream()
        .map(serviceService::findOptionallyById)
        .map(x -> x.orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<com.api.beautynote.model.master_service.MasterService> masterServices = masterServiceService.findAll()
        .stream()
        .filter(
            x -> (Objects.isNull(country) || x.getMaster().getUser().getCountry().equals(country)))
        .filter(x -> (Objects.isNull(region) || x.getMaster().getUser().getRegion().equals(region)))
        .filter(x -> (Objects.isNull(city) || x.getMaster().getUser().getCity().equals(city)))
        .filter(x -> (masterTypes.isEmpty() || containsAtLeastOne(masterTypes,
            x.getMaster().getMasterTypes())))
        .filter(x -> (serviceList.isEmpty() || serviceList.contains(x.getService())))
        .collect(Collectors.toList());

    return new MasterServiceArrayDto(masterServices);
  }

  @GetMapping("/masters/{masterId}")
  public PublicMasterProfileDto getMasterById(
      @PathVariable Long masterId
  ) {

    Master master = masterService.findById(masterId)
        .orElseThrow(() -> new MasterException(MasterExceptionProfile.MASTER_NOT_FOUND));

    return new PublicMasterProfileDto(master);
  }

  @GetMapping("/masters/{masterId}/services")
  public MasterServiceArrayDto getMasterServicesByMasterId(
      @PathVariable Long masterId
  ) {

    Master master = masterService.findById(masterId)
        .orElseThrow(() -> new MasterException(MasterExceptionProfile.MASTER_NOT_FOUND));

    List<com.api.beautynote.model.master_service.MasterService> masterServices = masterServiceService.findByMaster(
        master);

    return new MasterServiceArrayDto(masterServices);
  }

  @GetMapping("/masters/slots")
  public PublicSlotsMapDto getSlotsForRange(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startDateTime,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endDateTime,
      @RequestParam(required = false) List<Long> services,
      @RequestParam(required = false) List<Long> masters
  ) {

    services = (Objects.isNull(services)) ? new ArrayList<>() : services;
    masters = (Objects.isNull(masters)) ? new ArrayList<>() : masters;

    List<Service> serviceList = services.stream()
        .map(serviceService::findOptionallyById)
        .map(x -> x.orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<Master> masterList = masters.stream()
        .map(masterService::findOptionallyById)
        .map(x -> x.orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<Slot> slots = slotService.findAll().stream()
        .filter(x -> (Objects.isNull(startDateTime)) || x.getFrom().toInstant().atZone(ZoneId.systemDefault()).isAfter(startDateTime))
        .filter(x -> (Objects.isNull(endDateTime)) || x.getTo().toInstant().atZone(ZoneId.systemDefault()).isBefore(endDateTime))
        .filter(x -> (serviceList.isEmpty() || containsAtLeastOne(serviceList,
            masterServiceService.findByMaster(x.getMaster()).stream()
                .map(com.api.beautynote.model.master_service.MasterService::getService)
                .collect(Collectors.toList()))))
        .filter(x -> (masterList.isEmpty() || masterList.contains(x.getMaster())))
        .collect(Collectors.toList());

    return new PublicSlotsMapDto(slots);
  }

  @GetMapping("/masters/slots/{slotId}")
  public PublicSlotDto getSlotById(
      @PathVariable Long slotId
  ) {

    Slot slot = slotService.findById(slotId)
        .orElseThrow(() -> new SlotException(SlotExceptionProfile.SLOT_NOT_FOUND));

    return new PublicSlotDto(slot);
  }

  @PatchMapping("/masters/slots/{slotId}")
  public PublicSlotDto sendBookingRequest(
      @PathVariable Long slotId,
      @RequestBody BookingRequestDto bookingRequestDto,
      Authentication authentication
  ) {

    Slot slot = slotService.findById(slotId)
        .orElseThrow(() -> new SlotException(SlotExceptionProfile.SLOT_NOT_FOUND));

    if (slot.getStatus() != SlotStatus.AVAILABLE) {
      throw  new SlotException(SlotExceptionProfile.SLOT_IS_OCCUPIED);
    }

    if (slot.getTo().toInstant().atZone(ZoneId.systemDefault())
        .isBefore(new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault()))) {
      throw new SlotException(SlotExceptionProfile.SLOT_IN_PAST);
    }

    Service service = serviceService.findById(bookingRequestDto.getServiceId())
        .orElseThrow(() -> new ServiceException(ServiceExceptionProfile.SERVICE_NOT_FOUND));

    com.api.beautynote.model.master_service.MasterService masterService = masterServiceService.findById(new MasterServiceKey(slot.getMaster().getId(), service.getId()))
        .orElseThrow(() -> new MasterServiceException(MasterServiceExceptionProfile.MASTER_SERVICE_NOT_FOUND));

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User client = userService.findByEmail(jwtUserDetails.getEmail());

    slot.setUser(client);
    slot.setMasterService(masterService);
    slot.setStatus(SlotStatus.PENDING);

    //TODO: PUSH event

    slot = slotService.save(slot);

    return new PublicSlotDto(slot);
  }

  /**
   * 	/masters/{masterId}/services/{serviceId}
   * 	/masters/{masterId}/services/{serviceId}/reviews
   */
}
