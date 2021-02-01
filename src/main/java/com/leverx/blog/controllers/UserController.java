package com.leverx.blog.controllers;

import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.MessageResponse;
import com.leverx.blog.services.UserService;
import com.leverx.blog.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<?> getAllUsers() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserByID(@PathVariable final Long id) {
    return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserById(
      @PathVariable final Long id, @Valid @RequestBody final UserRequest userRequest) {
    if (id.equals(getAuthenticationUserID())) {
      userRequest.setId(id);
      return new ResponseEntity<>(userService.update(userRequest), HttpStatus.OK);
    }
    return new ResponseEntity<>(new MessageResponse("You have no rights"), HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById(@PathVariable final Long id) {
    if (id.equals(getAuthenticationUserID())) {
      userService.deleteById(id);
    }
    return new ResponseEntity<>(new MessageResponse("You have no rights"), HttpStatus.BAD_REQUEST);
  }

  public Long getAuthenticationUserID() {
    return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        ? ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getId()
        : 0;
  }
}
