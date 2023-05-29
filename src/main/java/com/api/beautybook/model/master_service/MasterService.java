package com.api.beautybook.model.master_service;

import com.api.beautybook.model.master.Master;
import com.api.beautybook.model.service.Service;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "master_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterService {

  @EmbeddedId
  private MasterServiceKey masterServiceKey = new MasterServiceKey();

  @ManyToOne
  @JoinColumn(name = "master_id")
  @MapsId("masterId")
  private Master master;

  @ManyToOne
  @JoinColumn(name = "service_id")
  @MapsId("serviceId")
  private Service service;

  private Double price;

  @Column(name = "average_time")
  private Integer averageTime;
}
