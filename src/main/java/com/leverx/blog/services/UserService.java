package com.leverx.blog.services;

import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;

import java.util.Optional;

/** @author Andrey Egorov */
public interface UserService {

  User save(User save);

  UserResponse save(UserRequest userRequest);

  UserResponse update(UserRequest userRequest);

  UserResponse findById(Long id);

  void deleteById(Long id);

  UserResponse findByEmail(String email);

  boolean existsByEmail(String email);

  String saveUserToRedis(User user);

  Optional<User> getUserByTokenFromRedis(String token);

  void removeUserByTokenFromRedis(String token);
}
