package com.api.beautynote.controllers.dto.requests;

import com.api.beautynote.model.master_service.MasterService;
import com.api.beautynote.model.master_service.MasterServiceKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MasterServiceRequestDto {

  private Long serviceId;
  private Double price;
  private Integer averageTime;
  private String description;
}
