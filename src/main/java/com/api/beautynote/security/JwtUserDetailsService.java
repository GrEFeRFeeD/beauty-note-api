package com.api.beautynote.security;

import com.api.beautynote.model.image.Image;
import com.api.beautynote.model.user.Role;
import com.api.beautynote.model.user.User;
import com.api.beautynote.model.user.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Custom UserDetailsService that implement work with JWT.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  /**
   * Wrapper method of loadUserByUsername for loading user by email and name.
   *
   * @param email email of certain user
   * @param name name of certain user
   * @return standard loaded UserDetails object
   */
  public UserDetails loadUserByEmailNameImage(String email, String name, String lastName, Image image) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) loadUserByUsername(email);
    jwtUserDetails.setName(name);
    jwtUserDetails.setLastName(lastName);

    User user = userRepository.findByEmail(email);
    if (user.getName() == null) {
      user.setName(name);
      user = userRepository.save(user);
    }
    if (user.getSurname() == null) {
      user.setSurname(lastName);
      user = userRepository.save(user);
    }
    if (user.getImage() == null) {
      user.setImage(image);
      user = userRepository.save(user);
    }

    return jwtUserDetails;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email);

    Set<GrantedAuthority> authorities = new HashSet<>();

    if (user == null) {
      user  = new User(null, null, email, null, null, null, null, null, null, null, Role.IN_DECIDE);
      user = userRepository.save(user);
    }

    if (user.getRole() != null) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
    }

    return new JwtUserDetails(email, null, passwordEncoder().encode(email), authorities);
  }
}