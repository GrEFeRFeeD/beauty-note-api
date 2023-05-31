package com.api.beautynote.controllers.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotGenerationProfileDto {

  private Long id;
  private String name;
  private Boolean isDefault;
}
