package com.api.beautynote.controllers.dto.responses;

import java.time.DayOfWeek;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SgpRuleDto {

  private Long id;
  private Long sgpId;
  private List<DayOfWeek> days;
  private TimeRangeDto dayLimit;
  private List<TimeRangeDto> lunches;
  private Integer slotFrequency;
  private Integer slotDuration;
}
