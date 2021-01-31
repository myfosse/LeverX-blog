package com.leverx.blog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

  @NotBlank
  @Size(max = 60)
  @Email
  private String email;

  @NotBlank
  @Size(min = 8, max = 60)
  private String password;

  @NotBlank
  @Size(min = 8, max = 60)
  private String passwordConfirmation;
}
