package com.api.beautybook.controllers.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FbInfoDto {

  private String clientId;
  private String redirectUri;
  private String tokenRequestUrl;
}
