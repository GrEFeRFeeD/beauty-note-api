package com.api.beautynote.model.master_type;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterTypeService {

  private final MasterTypeRepository masterTypeRepository;

  @Autowired
  public MasterTypeService(MasterTypeRepository masterTypeRepository) {
    this.masterTypeRepository = masterTypeRepository;
  }

  public List<MasterType> findAll() {
    return masterTypeRepository.findAll();
  }
}
