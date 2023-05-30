package com.api.beautybook.model.service;

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
}
