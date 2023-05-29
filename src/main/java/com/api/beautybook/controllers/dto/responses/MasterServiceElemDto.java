package com.api.beautybook.controllers.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterServiceElemDto {

  private Long serviceId;
  private Double price;
  private Integer averageTime;
  private Double rating;
}
