package com.api.beautynote.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByEmail(String email) {

    return userRepository.findByEmail(email);
  }

  public User save(User user) {
    return userRepository.save(user);
  }
}
