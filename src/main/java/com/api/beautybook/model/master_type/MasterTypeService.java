package com.api.beautybook.model.master_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterTypeService {

  private final MasterTypeRepository masterTypeRepository;

  @Autowired
  public MasterTypeService(MasterTypeRepository masterTypeRepository) {
    this.masterTypeRepository = masterTypeRepository;
  }
}
