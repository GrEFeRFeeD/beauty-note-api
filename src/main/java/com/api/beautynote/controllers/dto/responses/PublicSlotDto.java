package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.slot.SlotStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicSlotDto {

  private Long id;
  private Long serviceId;
  private Long masterId;
  private Date from;
  private Date to;
  private SlotStatus status;
}
