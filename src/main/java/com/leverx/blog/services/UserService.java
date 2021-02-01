package com.leverx.blog.services;

import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;

import java.util.List;

/** @author Andrey Egorov */
public interface UserService {

  User save(final User save);

  UserResponse save(final UserRequest userRequest);

  UserResponse update(final UserRequest userRequest);

  UserResponse findById(final Long id);

  List<UserResponse> getAll();

  void deleteById(final Long id);

  UserResponse findByEmail(final String email);

  User getByEmail(final String email);

  String saveUserToRedis(final User user);

  User getUserByTokenFromRedis(final String token);

  void removeUserByTokenFromRedis(final String token);
}
