package com.leverx.blog.repositories;

import com.leverx.blog.entities.User;

import java.util.Optional;

/** @author Andrey Egorov */
public interface RedisRepository {

  String saveUserToRedis(final User user);

  Optional<User> getUserByTokenFromRedis(final String token);

  void removeUserByTokenFromRedis(final String token);
}
