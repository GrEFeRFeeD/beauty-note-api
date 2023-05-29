package com.api.beautybook.model.master_type;

import com.api.beautybook.model.master.Master;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "master_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "master_type_id")
  private Long id;

  @Column(unique = true)
  private String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MasterType that = (MasterType) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
