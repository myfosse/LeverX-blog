package com.leverx.blog.services.impl;

import com.leverx.blog.converters.UserConverter;
import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;
import com.leverx.blog.repositories.UserRedisRepository;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

/** @author Andrey Egorov */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserRedisRepository userRedisRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      UserRedisRepository userRedisRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userRedisRepository = userRedisRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public UserResponse save(UserRequest userRequest) {
    User user = UserConverter.convertRequestToEntity(userRequest);
    user.setCreatedAt(LocalDate.now());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return UserConverter.convertEntityToResponse(userRepository.save(user));
  }

  @Override
  public UserResponse update(UserRequest userRequest) {
    User user = UserConverter.convertRequestToEntity(userRequest);
    user.setId(userRequest.getId());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
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

  @Override
  public UserResponse findByEmail(String email) {
    return UserConverter.convertEntityToResponse(
        userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public String saveUserToRedis(User user) {
    return userRedisRepository.saveUserToRedis(user);
  }

  @Override
  public Optional<User> getUserByTokenFromRedis(String token) {
    return userRedisRepository.getUserByTokenFromRedis(token);
  }

  @Override
  public void removeUserByTokenFromRedis(String token) {
    userRedisRepository.removeUserByTokenFromRedis(token);
  }
}
