package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.slot.Slot;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicSlotsMapDto {

  private List<PublicSlotDto> slots;

  public PublicSlotsMapDto(List<Slot> slots) {
    this.slots = slots.stream().map(PublicSlotDto::new).collect(Collectors.toList());
  }

//  private Map<LocalDate, List<PublicSlotDto>> slots;
//
//  public PublicSlotsMapDto(List<Slot> slots) {
//    this.slots = slots.isEmpty() ? new HashMap<>() :
//        slots.stream()
//        .map(PublicSlotDto::new)
//        .collect(Collectors.groupingBy(slot -> slot.getFrom()
//            .toInstant()
//            .atZone(ZoneId.systemDefault())
//            .toLocalDate()));
//  }
}
