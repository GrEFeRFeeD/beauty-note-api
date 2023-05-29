package com.api.beautybook.controllers.dto.responses;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeDto {

  private Date startTime;
  private Date endTime;
}
