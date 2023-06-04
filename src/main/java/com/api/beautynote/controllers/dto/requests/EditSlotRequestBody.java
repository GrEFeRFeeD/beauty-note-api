package com.api.beautynote.controllers.dto.requests;

import com.api.beautynote.model.slot.SlotStatus;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditSlotRequestBody {

  private SlotStatus status;

//  @DateTimeFormat(iso = ISO.DATE_TIME)
//  private ZonedDateTime from;
//
//  @DateTimeFormat(iso = ISO.DATE_TIME)
//  private ZonedDateTime to;
}
