package com.api.beautybook.controllers.dto.responses;

import com.api.beautybook.model.master_type.MasterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterTypeDto {

  private Long id;
  private String name;
  private byte[] image;

  public MasterTypeDto(MasterType masterType) {
    this.id = masterType.getId();
    this.name = masterType.getName();
    this.image = masterType.getImage().getContent();
  }
}
