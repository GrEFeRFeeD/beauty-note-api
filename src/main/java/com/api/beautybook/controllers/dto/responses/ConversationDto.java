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
public class ConversationDto {

  private Long id;
  private Long masterId;
  private Long userId;
  private List<MessageDto> messages;
}
