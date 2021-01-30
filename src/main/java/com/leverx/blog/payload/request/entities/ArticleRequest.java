package com.leverx.blog.payload.request.entities;

import com.leverx.blog.entities.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 100)
  private String title;

  @NotBlank
  @Size(min = 10, max = 10000)
  private String text;

  private EStatus status;

  private Long userID;

  @NotBlank private Set<TagRequest> tags;
}
