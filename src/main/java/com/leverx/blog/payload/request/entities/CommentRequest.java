package com.leverx.blog.payload.request.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 100)
  private String message;

  private Long articleID;

  private Long userID;
}
