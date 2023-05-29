package com.api.beautybook.model.time_range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "time_ranges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "time_range_id")
  private Long id;

  @Temporal(TemporalType.TIME)
  private Date startTime;

  @Temporal(TemporalType.TIME)
  private Date endTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeRange timeRange = (TimeRange) o;
    return Objects.equals(id, timeRange.id) && Objects.equals(startTime,
        timeRange.startTime) && Objects.equals(endTime, timeRange.endTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, startTime, endTime);
  }
}
