package com.api.beautynote.model.slot;

import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotService {

  private final SlotRepository slotRepository;

  @Autowired
  public SlotService(SlotRepository slotRepository) {
    this.slotRepository = slotRepository;
  }

  public List<Slot> findAll() {
    return slotRepository.findAll();
  }

  public Optional<Slot> findById(Long id) {
    return slotRepository.findById(id);
  }

  public Slot save(Slot slot) {
    return slotRepository.save(slot);
  }

  public List<Slot> findByClient(User client) {
    return slotRepository.findByUser(client);
  }

  public List<Slot> findByMaster(Master master) {
    return slotRepository.findByMaster(master);
  }
}
