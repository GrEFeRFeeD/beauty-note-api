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
public class MasterProfileDto {

  private Long id;
  private String country;
  private String region;
  private String city;
  private String address;
  private String name;
  private String surname;
  private Byte[] image;
  private List<MasterTypeDto> types;
  private String mobilePhone;
  private String email;
}
