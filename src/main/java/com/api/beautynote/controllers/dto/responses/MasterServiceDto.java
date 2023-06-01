package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.master_service.MasterService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterServiceDto {

  private Long serviceId;
  private Long masterId;
  private Double price;
  private Integer averageTime;
  private List<Long> reviews = new ArrayList<>();

  public MasterServiceDto(MasterService masterService) {
    this.serviceId = masterService.getService().getId();
    this.masterId = masterService.getMaster().getId();
    this.price = masterService.getPrice();
    this.averageTime = masterService.getAverageTime();
  }
}
