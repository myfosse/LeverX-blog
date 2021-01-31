package com.leverx.blog.controllers;

import com.leverx.blog.converters.UserConverter;
import com.leverx.blog.entities.User;
import com.leverx.blog.jwt.JwtUtils;
import com.leverx.blog.payload.request.SigninRequest;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.JwtResponse;
import com.leverx.blog.services.EmailService;
import com.leverx.blog.services.UserService;
import com.leverx.blog.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/** @author Andrey Egorov */
@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final EmailService emailService;
  private final JwtUtils jwtUtils;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      EmailService emailService,
      UserService userService,
      JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.emailService = emailService;
    this.userService = userService;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/sign-in")
  public @ResponseBody ResponseEntity<?> authenticateUser(
      @Valid @RequestBody final SigninRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    return ResponseEntity.ok(
        new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getFirstName(),
            userDetails.getLastName(),
            userDetails.getEmail()));
  }

  @PostMapping("/sign-up")
  public @ResponseBody String registerUser(
      @Valid @RequestBody final UserRequest userRequest, HttpServletRequest request)
      throws MessagingException {
    String token = userService.saveUserToRedis(UserConverter.convertRequestToEntity(userRequest));
    request.setAttribute("token", token);
    request.setAttribute("email", userRequest.getEmail());
    emailService.sendMessageForSendResetCodeToMail(userRequest.getEmail(), token);
    return "forward:/api/v1/email/send/confirm-email-message";
  }

  @GetMapping("/confirm")
  public ResponseEntity<String> saveUser(@Param(value = "token") final String token) {
    User user = userService.getUserByTokenFromRedis(token).orElse(null);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    userService.save(user);
    userService.removeUserByTokenFromRedis(token);
    return new ResponseEntity<>("You successfully confirm yor email!", HttpStatus.CREATED);
  }
}
