package com.api.beautynote.config;

import com.api.beautynote.security.FilterChainExceptionHandler;
import com.api.beautynote.security.JwtAccessDeniedHandler;
import com.api.beautynote.security.JwtAuthenticationEntryPoint;
import com.api.beautynote.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Security configuration.
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final UserDetailsService jwtUserDetailsService;
  private final JwtRequestFilter jwtRequestFilter;
  private final FilterChainExceptionHandler filterChainExceptionHandler;
  private final PasswordEncoder passwordEncoder;

  /**
   * Constructor.
   */
  @Autowired
  public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtAccessDeniedHandler jwtAccessDeniedHandler, UserDetailsService jwtUserDetailsService,
      JwtRequestFilter jwtRequestFilter, FilterChainExceptionHandler filterChainExceptionHandler,
      PasswordEncoder passwordEncoder) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtRequestFilter = jwtRequestFilter;
    this.filterChainExceptionHandler = filterChainExceptionHandler;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Defining the custom UserDetailsService and password encoder.
   */
  @Bean
  public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(jwtUserDetailsService)
        .passwordEncoder(passwordEncoder);
    return authenticationManagerBuilder.build();
  }

  /**
   * Configuring requests security.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf().disable()
        .cors().and()

        .headers().addHeaderWriter(
            new StaticHeadersWriter("Access-Control-Allow-Origin", "*")
        ).and()

        .authorizeRequests()
        .antMatchers("/authenticate", "/oauth2/facebook/v15.0").permitAll()

        .anyRequest().authenticated().and()

        // Add custom handling for unauthenticated and access denied errors
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // Add a filter to validate the tokens with every request
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        // ExceptionHandler filter
        .addFilterBefore(filterChainExceptionHandler, JwtRequestFilter.class);

    return http.build();
  }

}
