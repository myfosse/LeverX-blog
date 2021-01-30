package com.leverx.blog.payload.request.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;

  private Long articleID;

  private Long userID;
}
