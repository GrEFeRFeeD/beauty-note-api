package com.api.beautybook.model.master_service;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Service
public class MasterServiceKey implements Serializable {

  private Long masterId;
  private Long serviceId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MasterServiceKey that = (MasterServiceKey) o;
    return masterId.equals(that.masterId) && serviceId.equals(that.serviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(masterId, serviceId);
  }
}
