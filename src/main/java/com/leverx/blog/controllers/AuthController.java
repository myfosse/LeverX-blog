package com.leverx.blog.controllers;

import com.leverx.blog.converters.UserConverter;
import com.leverx.blog.entities.User;
import com.leverx.blog.jwt.JwtUtils;
import com.leverx.blog.payload.request.ForgotPasswordRequest;
import com.leverx.blog.payload.request.ResetPasswordRequest;
import com.leverx.blog.payload.request.SigninRequest;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.JwtResponse;
import com.leverx.blog.payload.response.MessageResponse;
import com.leverx.blog.services.EmailService;
import com.leverx.blog.services.UserService;
import com.leverx.blog.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final UserService userService;
  private final EmailService emailService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @Autowired
  public AuthController(
      final AuthenticationManager authenticationManager,
      final EmailService emailService,
      final UserService userService,
      final JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.emailService = emailService;
    this.userService = userService;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  public @ResponseBody ResponseEntity<?> registerUser(
      @Valid @RequestBody final UserRequest userRequest) throws MessagingException {
    String token = userService.saveUserToRedis(UserConverter.convertRequestToEntity(userRequest));
    emailService.sendMessageForConfirmAccount(userRequest.getEmail(), token);
    return ResponseEntity.ok(new MessageResponse("Confirm you account on email"));
  }

  @GetMapping("/confirm/{token}")
  public ResponseEntity<?> confirmUser(@PathVariable final String token) {
    User user = userService.getUserByTokenFromRedis(token);
    userService.save(user);
    userService.removeUserByTokenFromRedis(token);
    return new ResponseEntity<>(
        new MessageResponse("You successfully confirm yor account!"), HttpStatus.CREATED);
  }

  @PostMapping("/sign-in")
  public @ResponseBody ResponseEntity<?> signin(
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

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(
      @Valid @RequestBody final ForgotPasswordRequest forgotPasswordRequest)
      throws MessagingException {
    User user = userService.getByEmail(forgotPasswordRequest.getEmail());
    String token = userService.saveUserToRedis(user);
    emailService.sendMessageForResetPassword(forgotPasswordRequest.getEmail(), token);
    return ResponseEntity.ok(new MessageResponse("Check your email for reset password"));
  }

  @PostMapping("/reset/{token}")
  public @ResponseBody ResponseEntity<?> createPassword(
      @PathVariable final String token,
      @Valid @RequestBody final ResetPasswordRequest resetPasswordRequest) {

    if (!resetPasswordRequest
        .getPassword()
        .equals(resetPasswordRequest.getPasswordConfirmation())) {
      return new ResponseEntity<>(
          new MessageResponse("Error: Passwords doesn't match!"), HttpStatus.BAD_REQUEST);
    }

    User user = userService.getUserByTokenFromRedis(token);
    user.setPassword(resetPasswordRequest.getPassword());
    userService.save(user);
    userService.removeUserByTokenFromRedis(token);

    return ResponseEntity.ok(
            new MessageResponse("Successfully created a new password for the user with email: "
            + resetPasswordRequest.getEmail()));
  }
}
