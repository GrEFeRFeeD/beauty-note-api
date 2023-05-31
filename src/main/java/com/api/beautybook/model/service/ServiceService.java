package com.api.beautybook.model.service;

import com.api.beautybook.controllers.dto.responses.ServiceListDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

  private final ServiceRepository serviceRepository;

  @Autowired
  public ServiceService(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }

  public List<com.api.beautybook.model.service.Service> findAll() {
    return serviceRepository.findAll();
  }
}
