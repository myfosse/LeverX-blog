package com.leverx.blog.services;

import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.payload.response.entities.UserResponse;

/**
 * @author Andrey Egorov
 */
public interface UserService {

    UserResponse save(UserRequest userRequest);

    UserResponse update(UserRequest userRequest);

    UserResponse findById(Long id);

    void deleteById(Long id);
}
