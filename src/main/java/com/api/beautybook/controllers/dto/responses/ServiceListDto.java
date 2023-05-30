package com.api.beautybook.controllers.dto.responses;

import com.api.beautybook.model.service.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ServiceListDto {

  private List<ServiceDto> services;

  public ServiceListDto(List<Service> services) {
    this.services = services.stream().map(ServiceDto::new).collect(Collectors.toList());
  }
}
