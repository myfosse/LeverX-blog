package com.leverx.blog.converters;

import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

/** @author Andrey Egorov */
public class UserConverter {

  public static User convertRequestToEntity(UserRequest userRequest) {
    return User.builder()
        .firstName(userRequest.getFirstName())
        .lastName(userRequest.getLastName())
        .password(userRequest.getPassword())
        .email(userRequest.getEmail())
        .build();
  }

  public static UserResponse convertEntityToResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .createdAt(user.getCreatedAt())
        .build();
  }

  public static List<User> convertListOfRequestToEntity(List<UserRequest> userRequestList) {
    return userRequestList.stream()
        .map(UserConverter::convertRequestToEntity)
        .collect(Collectors.toList());
  }

  public static List<UserResponse> convertListOfEntityToRequest(List<User> userList) {
    return userList.stream()
        .map(UserConverter::convertEntityToResponse)
        .collect(Collectors.toList());
  }
}
