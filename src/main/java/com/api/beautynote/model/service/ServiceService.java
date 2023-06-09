package com.api.beautynote.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

  private final ServiceRepository serviceRepository;

  @Autowired
  public ServiceService(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }

  public List<com.api.beautynote.model.service.Service> findAll() {
    return serviceRepository.findAll();
  }

  public Optional<com.api.beautynote.model.service.Service> findOptionallyById(Long id) {
    return serviceRepository.findById(id);
  }

  public Optional<com.api.beautynote.model.service.Service> findById(Long id) {
    return serviceRepository.findById(id);
  }
}
