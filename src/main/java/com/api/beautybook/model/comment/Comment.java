package com.api.beautybook.model.comment;

import com.api.beautybook.model.review.Review;
import com.api.beautybook.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "review_id")
  private Review review;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String text;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dateTime;
}
