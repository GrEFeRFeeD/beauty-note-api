package com.api.beautynote.controllers.dto.responses;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

  private Long id;
  private Long reviewId;
  private Long userId;
  private String text;
  private Date dateTime;
}
