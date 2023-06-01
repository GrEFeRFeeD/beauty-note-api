package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.requests.JwtDto;
import com.api.beautynote.controllers.dto.responses.FbInfoDto;
import com.api.beautynote.exceptions.SecurityException;
import com.api.beautynote.exceptions.SecurityException.SecurityExceptionProfile;
import com.api.beautynote.model.image.Image;
import com.api.beautynote.model.image.ImageService;
import com.api.beautynote.security.JwtUserDetails;
import com.api.beautynote.security.JwtUserDetailsService;
import com.api.beautynote.utils.FacebookUtil;
import com.api.beautynote.utils.FacebookUtil.FacebookScopes;
import com.api.beautynote.utils.JwtUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * Controller for authentication and authenticated requests.
 */
@CrossOrigin
@RestController
@Transactional
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final JwtUserDetailsService userDetailsService;
  private final FacebookUtil facebookUtil;
  private ImageService imageService;

  @Autowired
  public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      JwtUserDetailsService userDetailsService, FacebookUtil facebookUtil,
      ImageService imageService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.facebookUtil = facebookUtil;
    this.imageService = imageService;
  }

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody JwtDto jwtDto) {

    Map<FacebookScopes, String> userScopes;
    try {
      userScopes = facebookUtil
          .getScope(jwtDto.getFacebookToken());
    } catch (RestClientException e) {
      throw new SecurityException(SecurityExceptionProfile.BAD_FACEBOOK_TOKEN);
    }

    String email = userScopes.get(FacebookScopes.EMAIL);
    String firstName = userScopes.get(FacebookScopes.FIRST_NAME);
    String lastName = userScopes.get(FacebookScopes.LAST_NAME);
    String pictureUrl = userScopes.get(FacebookScopes.PROFILE_PICTURE_URL);

    byte[] pictureContent = obtainImageContentByUrl(pictureUrl);
    System.out.println("BYTE CONTENT = " + pictureContent);
    Image savedImage = imageService.saveImage(pictureContent);
    System.out.println("IMAGE = " + savedImage);

    authenticate(email);

    final JwtUserDetails userDetails = (JwtUserDetails) userDetailsService
        .loadUserByEmailNameImage(email, firstName, lastName, savedImage);

    com.api.beautynote.controllers.dto.responses.JwtDto jwtResponseDto = new com.api.beautynote.controllers.dto.responses.JwtDto(jwtUtil.generateToken(userDetails));

    return ResponseEntity.ok(jwtResponseDto);
  }

  private void authenticate(String username) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, username));
    } catch (BadCredentialsException e) {
      throw new SecurityException(SecurityExceptionProfile.BAD_CREDENTIALS);
    }
  }

  private byte[] obtainImageContentByUrl(String imageUrl) {

    try {

      URL url = new URL(imageUrl);

      InputStream inputStream = url.openStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }

      return outputStream.toByteArray();

    } catch (IOException e) {

    }

    return new byte[10];
  }

  @GetMapping("/oauth2/facebook/v15.0")
  public FbInfoDto getFacebookClientId(
      @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
      String facebookClientId,
      @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}")
      String redirectUri) {

    String requestUrl = String.format(FacebookUtil.userFacebookTokenUrlV15,
        facebookClientId, redirectUri) + "&scope=public_profile,email";

    return new FbInfoDto(facebookClientId, redirectUri, requestUrl);
  }
}