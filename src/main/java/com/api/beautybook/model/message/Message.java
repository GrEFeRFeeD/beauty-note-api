package com.api.beautybook.model.message;

import com.api.beautybook.model.conversation.Conversation;
import com.api.beautybook.model.image.Image;
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
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "conversation_id")
  private Conversation conversation;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String text;

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dateTime;
}
