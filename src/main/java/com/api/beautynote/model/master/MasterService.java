package com.api.beautynote.model.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

  private final MasterRepository masterRepository;

  @Autowired
  public MasterService(MasterRepository masterRepository) {
    this.masterRepository = masterRepository;
  }

  public Master save(Master master) {
    return masterRepository.save(master);
  }
}
