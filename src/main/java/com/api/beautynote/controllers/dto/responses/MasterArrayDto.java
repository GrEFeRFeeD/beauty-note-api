package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.master.Master;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MasterArrayDto {

  private List<PublicMasterProfileDto> masters;

  public MasterArrayDto(List<Master> masters) {
    this.masters = masters.stream().map(PublicMasterProfileDto::new).collect(Collectors.toList());
  }
}
