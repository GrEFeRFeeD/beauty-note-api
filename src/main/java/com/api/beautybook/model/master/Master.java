package com.api.beautybook.model.master;

import com.api.beautybook.model.master_type.MasterType;
import com.api.beautybook.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

@Entity
@Table(name = "masters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  private Boolean showMobilePhone;

  @Column(name = "show_email")
  private Boolean showEmail;

  @Column(name = "singly_booking_declination")
  private Boolean singlyBookingDeclination;

  @Column(name = "singly_booking_declination_policy")
  private Integer singlyBookingDeclinationPolicy;

  @Column(name = "slot_overlapping")
  private Boolean slotOverlapping;

  @Column(name = "booking_limit")
  private Integer bookingLimit;

  public void addMasterType(MasterType masterType) {
    this.masterTypes.add(masterType);
  }
}

