package com.leverx.blog.services;

import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.payload.response.TagRatingResponse;
import com.leverx.blog.payload.response.entities.TagResponse;

import java.util.List;

/** @author Andrey Egorov */
public interface TagService {

  TagResponse save(TagRequest tagRequest);

  TagResponse findById(Long id);

  TagResponse findByName(String name);

  List<TagRatingResponse> getTagsByRating();
}
