package com.api.beautynote.model.slot;

import com.api.beautynote.model.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {

  List<Slot> findByUser(User client);
}
