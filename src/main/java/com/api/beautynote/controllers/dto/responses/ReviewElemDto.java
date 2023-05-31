package com.api.beautynote.controllers.dto.responses;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewElemDto {

  private Long id;
  private Long userId;
  private Integer grade;
  private String text;
  private Date dateTime;
  private List<Byte[]> images;
}
