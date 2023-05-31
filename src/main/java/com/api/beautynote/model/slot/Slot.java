package com.api.beautynote.model.slot;

import com.api.beautynote.model.master.Master;
import com.api.beautynote.model.master_service.MasterService;
import com.api.beautynote.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "slot_id")
  private Long id;

  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "ms_master_id", referencedColumnName = "master_id"),
      @JoinColumn(name = "ms_service_id", referencedColumnName = "service_id")
  })
  private MasterService masterService;

  @ManyToOne
  @JoinColumn(name = "master_id")
  private Master master;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private User user;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "from_timestamp")
  private Date from;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "to_timestamp")
  private Date to;

  @Enumerated(EnumType.STRING)
  private SlotStatus status;
}
