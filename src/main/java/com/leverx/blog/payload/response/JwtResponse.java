package com.leverx.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Andrey Egorov */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String firstName;
  private String lastName;
  private String email;

  public JwtResponse(
      final String accessToken,
      final Long id,
      final String firstName,
      final String lastName,
      final String email) {
    this.token = accessToken;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
}
