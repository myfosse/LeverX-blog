package com.leverx.blog.repositories;

import com.leverx.blog.entities.User;

import java.util.Optional;

/** @author Andrey Egorov */
public interface RedisRepository {

  String saveUserToRedis(User user);

  Optional<User> getUserByTokenFromRedis(String token);

  void removeUserByTokenFromRedis(String token);
}
