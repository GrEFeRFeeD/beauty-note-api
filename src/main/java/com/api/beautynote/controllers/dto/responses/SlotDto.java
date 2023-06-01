package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.slot.Slot;
import com.api.beautynote.model.slot.SlotStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotDto {

  private Long id;
  private Long serviceId;
  private Long masterId;
  private Long clientId;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date from;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date to;
  private SlotStatus status;

  public SlotDto(Slot slot) {
    this.id = slot.getId();
    this.serviceId = Objects.nonNull(slot.getMasterService()) ? slot.getMasterService().getService().getId() : null;
    this.masterId = slot.getMaster().getId();
    this.clientId = (Objects.nonNull(slot.getUser())) ? slot.getUser().getId() : null;
    this.from = slot.getFrom();
    this.to = slot.getTo();
    this.status = slot.getStatus();
  }
}
