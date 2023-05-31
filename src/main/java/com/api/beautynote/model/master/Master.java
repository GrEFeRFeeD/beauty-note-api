package com.api.beautynote.model.master;

import com.api.beautynote.model.master_type.MasterType;
import com.api.beautynote.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "masters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Master {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "master_id")
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  private String address;

  @OneToMany
  @JoinColumn(name = "master_type_ids")
  private Set<MasterType> masterTypes = new HashSet<>();

  @Column(name = "show_mobile_phone")
  private Boolean showMobilePhone = false;

  @Column(name = "show_email")
  private Boolean showEmail = false;

  @Column(name = "singly_booking_declination")
  private Boolean singlyBookingDeclination = false;

  @Column(name = "singly_booking_declination_policy")
  private Integer singlyBookingDeclinationPolicy = 0;

  @Column(name = "slot_overlapping")
  private Boolean slotOverlapping = false;

  @Column(name = "booking_limit")
  private Integer bookingLimit = 0;

  public void addMasterType(MasterType masterType) {
    this.masterTypes.add(masterType);
  }

}

