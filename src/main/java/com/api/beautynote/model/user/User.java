package com.api.beautynote.model.user;

import com.api.beautynote.model.image.Image;
import com.api.beautynote.model.master.Master;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Master master;

  @Column(unique = true)
  private String email;

  private String name;
  private String surname;

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  private String country;
  private String region;
  private String city;
  private Role role;
  private String registrationToken = null;

  public User(Long id, Master master, String email, String name, String surname, String phoneNumber,
      Image image, String country, String region, String city, Role role) {
    this.id = id;
    this.master = master;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
    this.image = image;
    this.country = country;
    this.region = region;
    this.city = city;
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", master=" + Objects.nonNull(master) +
        ", email='" + email + '\'' +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", image=" + image +
        ", country='" + country + '\'' +
        ", region='" + region + '\'' +
        ", city='" + city + '\'' +
        ", role=" + role +
        '}';
  }
}
