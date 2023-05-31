package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.responses.MasterTypeListDto;
import com.api.beautynote.controllers.dto.responses.ServiceListDto;
import com.api.beautynote.model.master_type.MasterTypeService;
import com.api.beautynote.model.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

  private final ServiceService serviceService;
  private final MasterTypeService masterTypeService;

  @Autowired
  public DefaultController(ServiceService serviceService, MasterTypeService masterTypeService) {
    this.serviceService = serviceService;
    this.masterTypeService = masterTypeService;
  }

  @GetMapping("/master-types")
  public MasterTypeListDto getAllMasterTypes() {
    return new MasterTypeListDto(masterTypeService.findAll());
  }

  @GetMapping("/services")
  public ServiceListDto getAllServices() {
    return new ServiceListDto(serviceService.findAll());
  }
  /**
   * 	/masterTypes
   * 	/services
   */
}
