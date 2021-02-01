package com.leverx.blog.payload.response.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;
}
