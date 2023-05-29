package com.api.beautybook.controllers.dto.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewArrayDto {

  private Long serviceId;
  private Long masterId;
  private Double rating;
  private Integer reviewsCount;
  private List<ReviewElemDto> reviews;
}
