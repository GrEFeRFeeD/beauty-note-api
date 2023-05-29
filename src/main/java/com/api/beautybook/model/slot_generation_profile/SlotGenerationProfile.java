package com.api.beautybook.model.slot_generation_profile;

import com.api.beautybook.model.master.Master;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "slot_generation_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotGenerationProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "slot_generation_profile_id")
  private Long id;

  private String name;

  @Column(name = "is_default")
  private Boolean isDefault;

  @ManyToOne
  @JoinColumn(name = "master_id")
  private Master master;

}
