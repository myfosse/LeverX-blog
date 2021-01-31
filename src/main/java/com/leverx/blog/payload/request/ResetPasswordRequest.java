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

  @NotBlank(message = "Email can't be blank")
  @Size(max = 60, message = "Max length of email 60 symbols")
  @Email(message = "Email not valid")
  private String email;

  @NotBlank(message = "Password can't be blank")
  @Size(min = 8, max = 60)
  private String password;

  @NotBlank(message = "Password can't be blank")
  @Size(min = 8, max = 60)
  private String passwordConfirmation;
}
