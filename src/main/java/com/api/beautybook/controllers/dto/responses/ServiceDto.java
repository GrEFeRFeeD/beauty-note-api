package com.api.beautybook.controllers.dto.responses;

import com.api.beautybook.model.service.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

  private Long id;
  private String name;
  private MasterTypeDto type;

  public ServiceDto(Service service) {
    this.id = service.getId();
    this.name = service.getName();
    this.type = new MasterTypeDto(service.getMasterType());
  }
}
