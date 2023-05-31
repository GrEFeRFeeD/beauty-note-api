package com.api.beautynote.model.master_service;

import com.api.beautynote.model.master.Master;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterServiceRepository extends JpaRepository<MasterService, MasterServiceKey> {

  List<MasterService> findByMaster(Master master);
}
