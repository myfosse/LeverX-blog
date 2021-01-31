package com.leverx.blog.service;

import com.leverx.blog.converters.UserConverter;
import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.UserRequest;
import com.leverx.blog.repositories.RedisRepository;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.UserService;
import com.leverx.blog.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/** @author Andrey Egorov */
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {UserServiceImpl.class})
public class UserServiceTest {

  @Autowired private UserService userService;

  @MockBean private UserRepository userRepository;
  @MockBean private RedisRepository redisRepository;
  @MockBean private PasswordEncoder passwordEncoder;

  User actualInput;

  User expectedInput;

  List<User> actualList;

  List<User> expectedList;

  UserRequest userRequest;

  @BeforeEach
  public void setUp() {
    actualList =
        Collections.singletonList(
            new User(1L, "Andrey", "Egorov", "password", "email@mail.ru", LocalDate.now()));
    expectedList =
        Collections.singletonList(
            new User(1L, "Andrey", "Egorov", "password", "email@mail.ru", LocalDate.now()));
    actualInput = actualList.get(0);
    expectedInput = expectedList.get(0);
    userRequest =
        UserRequest.builder()
            .id(1L)
            .firstName("Andrey")
            .lastName("Egorov")
            .email("email@mail.ru")
            .password("password")
            .build();
  }

  @Test
  public void testSaveEntity() {

    when(userRepository.save(actualInput)).thenReturn(actualInput);

    when(passwordEncoder.encode("password")).thenReturn("password");

    assertEquals(expectedInput, userService.save(actualInput));
  }

  @Test
  public void testSaveRequest() {

    when(userRepository.save(actualInput)).thenReturn(actualInput);

    when(passwordEncoder.encode(actualInput.getPassword())).thenReturn(actualInput.getPassword());

    assertEquals(
        UserConverter.convertEntityToResponse(expectedInput), userService.save(userRequest));
  }

  @Test
  public void testUpdateRequest() {

    when(userRepository.findById(1L)).thenReturn(Optional.of(actualInput));

    when(userRepository.save(actualInput)).thenReturn(actualInput);

    when(passwordEncoder.encode("password")).thenReturn("password");

    assertEquals(
        UserConverter.convertEntityToResponse(expectedInput), userService.update(userRequest));
  }

  @Test
  public void testFindById() {
    Long id = 1L;

    when(userRepository.findById(id)).thenReturn(Optional.of(actualInput));

    assertEquals(
        UserConverter.convertEntityToResponse(expectedInput), userService.findById(id));
  }

  @Test
  public void testGetAll() {
    when(userRepository.findAll()).thenReturn(actualList);

    assertEquals(
        expectedList.stream()
            .map(UserConverter::convertEntityToResponse)
            .collect(Collectors.toList()),
        userService.getAll());
  }

  @Test
  public void testDelete() {
    Long id = 1L;

    doNothing().when(userRepository).deleteById(id);

    userService.deleteById(id);

    verify(userRepository).deleteById(id);
  }

  @Test
  public void testFindByUsername() {
    when(userRepository.findByEmail(actualInput.getEmail())).thenReturn(Optional.of(actualInput));

    assertEquals(
        UserConverter.convertEntityToResponse(expectedInput),
        userService.findByEmail(actualInput.getEmail()));
  }

  @Test
  public void testGetByEmail() {
    when(userRepository.findByEmail(actualInput.getEmail())).thenReturn(Optional.of(actualInput));

    assertEquals(expectedInput, userService.getByEmail(actualInput.getEmail()));
  }

  @Test
  public void testSaveUseroRedis() {
    when(redisRepository.saveUserToRedis(actualInput)).thenReturn(String.valueOf(actualInput));

    assertEquals(String.valueOf(expectedInput), userService.saveUserToRedis(actualInput));
  }

  @Test
  public void getUserByTokenFromRedis() {
    when(redisRepository.getUserByTokenFromRedis("token")).thenReturn(Optional.of(actualInput));

    assertEquals(expectedInput, userService.getUserByTokenFromRedis("token"));
  }

  @Test
  public void removeUserByTokenFromRedis() {
    String token = "token";

    doNothing().when(redisRepository).removeUserByTokenFromRedis(token);

    userService.removeUserByTokenFromRedis(token);

    verify(redisRepository).removeUserByTokenFromRedis(token);
  }
}
