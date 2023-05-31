package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.master_service.MasterService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MasterServiceArrayDto {

  private List<MasterServiceElemDto> services;

  public MasterServiceArrayDto(List<MasterService> masterServices) {
    this.services = masterServices.stream().map(MasterServiceElemDto::new).collect(Collectors.toList());
  }
}
