package com.api.beautynote.model.master;

import com.api.beautynote.model.master_type.MasterType;
import java.util.List;
import java.util.Optional;
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

  public List<Master> findAll() {
    return masterRepository.findAll();
  }

  public Optional<Master> findById(Long id) {
    return masterRepository.findById(id);
  }
}
