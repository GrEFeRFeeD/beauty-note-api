package com.api.beautybook.controllers.dto.responses;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

  private Long id;
  private Long conversationId;
  private Long senderId;
  private String text;
  private Byte[] image;
  private Date dateTime;
}
