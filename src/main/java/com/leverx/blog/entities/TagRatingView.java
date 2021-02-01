package com.leverx.blog.entities;

import org.springframework.beans.factory.annotation.Value;

/** @author Andrey Egorov */
public interface TagRatingView {

  @Value("#{target.id}")
  Long getId();

  @Value("#{target.name}")
  String getName();

  @Value("#{target.post_count}")
  int getPostCount();
}
