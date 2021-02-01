package com.leverx.blog.payload.request.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 100)
  private String firstName;

  @NotBlank
  @Size(min = 1, max = 100)
  private String lastName;

  @NotBlank private String password;

  @NotBlank
  @Size(max = 60)
  @Email
  private String email;
}