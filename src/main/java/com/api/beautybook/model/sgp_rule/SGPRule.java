package com.api.beautybook.model.sgp_rule;

import com.api.beautybook.model.slot_generation_profile.SlotGenerationProfile;
import com.api.beautybook.model.time_range.TimeRange;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sgp_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SGPRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sgp_rule_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sgp_id")
  private SlotGenerationProfile slotGenerationProfile;

  @ElementCollection
  private List<DayOfWeek> days = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "day_limit_trange_id")
  private TimeRange dayLimit;

  @OneToMany
  @JoinColumn(name = "lunches_trange_ids")
  private Set<TimeRange> lunches = new HashSet<>();

  @Column(name = "slot_frequency")
  private Integer slotFrequency;

  @Column(name = "slot_duration")
  private Integer slotDuration;
}
