package com.api.beautybook.controllers.dto.responses;

import com.api.beautybook.model.master_type.MasterType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MasterTypeListDto {

  private List<MasterTypeDto> masterTypes;

  public MasterTypeListDto(List<MasterType> masterTypes) {
    this.masterTypes = masterTypes.stream().map(MasterTypeDto::new).collect(Collectors.toList());
  }
}
