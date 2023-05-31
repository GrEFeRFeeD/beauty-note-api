package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.responses.MasterArrayDto;
import com.api.beautynote.controllers.dto.responses.MasterServiceArrayDto;
import com.api.beautynote.controllers.dto.responses.PublicMasterProfileDto;
import com.api.beautynote.exceptions.MasterException;
import com.api.beautynote.exceptions.MasterException.MasterExceptionProfile;
import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.master.MasterService;
import com.api.beautynote.model.master_service.MasterServiceService;
import com.api.beautynote.model.master_type.MasterType;
import com.api.beautynote.model.master_type.MasterTypeService;
import com.api.beautynote.model.service.Service;
import com.api.beautynote.model.service.ServiceService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterController {

  private final MasterService masterService;
  private final MasterTypeService masterTypeService;
  private final ServiceService serviceService;
  private final MasterServiceService masterServiceService;

  @Autowired
  public MasterController(MasterService masterService, MasterTypeService masterTypeService,
      ServiceService serviceService, MasterServiceService masterServiceService) {
    this.masterService = masterService;
    this.masterTypeService = masterTypeService;
    this.serviceService = serviceService;
    this.masterServiceService = masterServiceService;
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

  private static <T> boolean containsAtLeastOne(Collection<T> neededElements, Collection<T> actualElements) {
    for (T item: neededElements) {
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

    List<com.api.beautynote.model.master_service.MasterService> masterServices = masterServiceService.findAll().stream()
        .filter(x -> (Objects.isNull(country) || x.getMaster().getUser().getCountry().equals(country)))
        .filter(x -> (Objects.isNull(region) || x.getMaster().getUser().getRegion().equals(region)))
        .filter(x -> (Objects.isNull(city) || x.getMaster().getUser().getCity().equals(city)))
        .filter(x -> (masterTypes.isEmpty() || containsAtLeastOne(masterTypes, x.getMaster().getMasterTypes())))
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

    List<com.api.beautynote.model.master_service.MasterService> masterServices = masterServiceService.findByMaster(master);

    return new MasterServiceArrayDto(masterServices);
  }

  /**
   * 	/masters/{masterId}/services/{serviceId}
   * 	/masters/{masterId}/services/{serviceId}/reviews
   * 	/masters/slots
   * 	/masters/slots/{slotId}
   */
}
