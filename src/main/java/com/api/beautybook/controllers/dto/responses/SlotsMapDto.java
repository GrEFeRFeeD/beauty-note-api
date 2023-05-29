package com.api.beautybook.controllers.dto.responses;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotsMapDto {

  private Map<Date, List<SlotDto>> slots;
}
