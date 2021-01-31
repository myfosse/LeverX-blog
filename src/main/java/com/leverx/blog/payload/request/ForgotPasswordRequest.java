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
public class ForgotPasswordRequest {

  @NotBlank
  @Size(min = 4, max = 60)
  @Email
  private String email;
}
