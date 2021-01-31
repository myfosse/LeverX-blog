package com.leverx.blog.services;

import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.payload.response.TagRatingResponse;
import com.leverx.blog.payload.response.entities.TagResponse;

import java.util.List;

/** @author Andrey Egorov */
public interface TagService {

  TagResponse save(final TagRequest tagRequest);

  TagResponse findById(final Long id);

  TagResponse findByName(final String name);

  List<TagRatingResponse> getTagsByRating();
}
