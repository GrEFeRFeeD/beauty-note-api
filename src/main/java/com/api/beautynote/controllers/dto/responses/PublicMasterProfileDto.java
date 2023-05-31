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
@AllArgsConstructor
public class PublicMasterProfileDto {

  private Long id;
  private String country;
  private String region;
  private String city;
  private String address;
  private String name;
  private String surname;
  private byte[] image;
  private List<MasterTypeDto> types;
  private String phoneNumber;
  private String email;

  public PublicMasterProfileDto(Master master) {
    this.id = master.getId();
    this.country = master.getUser().getCountry();
    this.region = master.getUser().getRegion();
    this.city = master.getUser().getCity();
    this.address = master.getAddress();
    this.name = master.getUser().getName();
    this.surname = master.getUser().getSurname();
    this.image = master.getUser().getImage().getContent();
    this.types = master.getMasterTypes().stream().map(MasterTypeDto::new).collect(Collectors.toList());
    this.phoneNumber = (master.getShowMobilePhone()) ? master.getUser().getPhoneNumber() : "hidden";
    this.email = (master.getShowEmail()) ? master.getUser().getEmail() : "hidden";
  }
}
