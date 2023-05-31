package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.master_service.MasterService;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterServiceElemDto {

  private Long masterId;
  private Long serviceId;
  private Double price;
  private Integer averageTime;
  private Double rating;
  private String description;


  public MasterServiceElemDto(MasterService masterService) {
    this.masterId = masterService.getMaster().getId();
    this.serviceId = masterService.getService().getId();
    this.price = masterService.getPrice();
    this.averageTime = masterService.getAverageTime();
    this.description = masterService.getDescription();
    //TODO: master service rating calculation
    this.rating = (double) (3 + new Random().nextInt(3));
  }
}
