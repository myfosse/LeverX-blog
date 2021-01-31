package com.leverx.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** @author Andrey Egorov */
@Data
@Builder
@AllArgsConstructor
public class TagRatingResponse {

  private Long id;

  private String name;

  private int postCount;
}
