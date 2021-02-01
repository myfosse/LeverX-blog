package com.leverx.blog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

  @NotBlank @Email private String email;

  @NotBlank private String password;
}
