package com.api.beautynote.controllers.dto.responses;

import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.master_type.MasterType;
import com.api.beautynote.model.user.Role;
import com.api.beautynote.model.user.User;
import java.util.Set;
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
  private byte[] image;
  private Set<MasterType> types;
  private String phoneNumber;
  private String email;
  private Role role;

  public MasterProfileDto(User user) {
    this.id = user.getId();
    this.country = user.getCountry();
    this.region = user.getRegion();
    this.city = user.getRegion();
    this.city = user.getCity();
    this.address = null;
    this.name = user.getName();
    this.surname = user.getSurname();
    this.image = user.getImage().getContent();
    this.types = null;
    this.phoneNumber = user.getPhoneNumber();
    this.email = user.getEmail();
    this.role = user.getRole();
  }

  public MasterProfileDto(Master master) {
    this.id = master.getId();
    this.country = master.getUser().getCountry();
    this.region = master.getUser().getRegion();
    this.city = master.getUser().getRegion();
    this.city = master.getUser().getCity();
    this.address = master.getAddress();
    this.name = master.getUser().getName();
    this.surname = master.getUser().getSurname();
    this.image = master.getUser().getImage().getContent();
    this.types = master.getMasterTypes();
    this.phoneNumber = master.getUser().getPhoneNumber();
    this.email = master.getUser().getEmail();
    this.role = master.getUser().getRole();
  }
}
