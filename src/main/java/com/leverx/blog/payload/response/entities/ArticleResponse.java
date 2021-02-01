package com.leverx.blog.payload.response.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leverx.blog.entities.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

  private Long id;

  private String title;

  private String text;

  private EStatus status;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate updatedAt;

  private UserResponse userResponse;

  private Set<TagResponse> tags;
}
