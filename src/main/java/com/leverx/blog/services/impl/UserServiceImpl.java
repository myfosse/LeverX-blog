package com.leverx.blog.services.impl;

import com.leverx.blog.converters.UserConverter;
import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

/** @author Andrey Egorov */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserResponse save(UserRequest userRequest) {
    User user = UserConverter.convertRequestToEntity(userRequest);
    user.setCreatedAt(LocalDate.now());
    return UserConverter.convertEntityToResponse(userRepository.save(user));
  }

  @Override
  public UserResponse update(UserRequest userRequest) {
    User user = UserConverter.convertRequestToEntity(userRequest);
    user.setId(userRequest.getId());
    return UserConverter.convertEntityToResponse(userRepository.save(user));
  }

  @Override
  public UserResponse findById(Long id) {
    return UserConverter.convertEntityToResponse(
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
