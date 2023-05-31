package com.api.beautynote.controllers.dto.responses;

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
  private List<Long> reviews;
}
