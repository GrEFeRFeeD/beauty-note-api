package com.api.beautynote.model.master_service;

import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.master.MasterRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceService {

  private final MasterServiceRepository masterServiceRepository;

  @Autowired
  public MasterServiceService(MasterServiceRepository masterServiceRepository) {
    this.masterServiceRepository = masterServiceRepository;
  }

  public List<MasterService> findAll() {
    return masterServiceRepository.findAll();
  }

  public List<MasterService> findByMaster(Master master) {
    return masterServiceRepository.findByMaster(master);
  }

  public Optional<MasterService> findById(MasterServiceKey id) {
    return masterServiceRepository.findById(id);
  }

  public MasterService save(MasterService masterService) {
    return masterServiceRepository.save(masterService);
  }
}
