package com.api.beautybook.controllers.dto.responses;

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
public class ReviewDto {

  public Long id;
  public Long serviceId;
  public Long masterId;
  public Long userId;
  public Integer grade;
  private String text;
  private Date dateTime;
  private List<Byte[]> images;
  private List<Long> comments;
}
