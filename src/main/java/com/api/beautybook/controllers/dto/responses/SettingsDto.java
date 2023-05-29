package com.api.beautybook.controllers.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto {

  private Boolean showMobilePhone;
  private Boolean showEmail;
  private Boolean singlyBookingDeclination;
  private Integer singlyBookingDeclinationPolicy;
  private Boolean slotOverlapping;
  private Integer bookingLimit;
}
