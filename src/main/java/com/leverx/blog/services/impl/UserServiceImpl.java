package com.leverx.blog.services.impl;

import com.leverx.blog.converters.UserConverter;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.CustomUserException;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;
import com.leverx.blog.repositories.RedisRepository;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

/** @author Andrey Egorov */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RedisRepository redisRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      RedisRepository redisRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.redisRepository = redisRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User save(User user) {
    user.setCreatedAt(LocalDate.now());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
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
  public User getByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public String saveUserToRedis(User user) {
    return redisRepository.saveUserToRedis(user);
  }

  @Override
  public User getUserByTokenFromRedis(String token) {
    return redisRepository.getUserByTokenFromRedis(token).orElseThrow(CustomUserException::new);
  }

  @Override
  public void removeUserByTokenFromRedis(String token) {
    redisRepository.removeUserByTokenFromRedis(token);
  }
}
