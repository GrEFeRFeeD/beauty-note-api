package com.api.beautybook.model.user;

import com.api.beautybook.model.image.Image;
import com.api.beautybook.model.master.Master;
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
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

}
