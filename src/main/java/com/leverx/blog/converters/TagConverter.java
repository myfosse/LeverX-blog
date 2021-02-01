package com.leverx.blog.converters;

import com.leverx.blog.entities.Tag;
import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.payload.response.entities.TagResponse;

import java.util.Set;
import java.util.stream.Collectors;

/** @author Andrey Egorov */
public class TagConverter {

  public static Tag convertRequestToEntity(final TagRequest tagRequest) {
    return Tag.builder().name(tagRequest.getName()).build();
  }

  public static TagResponse convertEntityToResponse(final Tag tag) {
    return TagResponse.builder().id(tag.getId()).name(tag.getName()).build();
  }

  public static Set<Tag> convertSetOfRequestToEntity(final Set<TagRequest> tagRequestSet) {
    return tagRequestSet.stream()
        .map(TagConverter::convertRequestToEntity)
        .collect(Collectors.toSet());
  }

  public static Set<TagResponse> convertSetOfEntityToResponse(final Set<Tag> tagSet) {
    return tagSet.stream().map(TagConverter::convertEntityToResponse).collect(Collectors.toSet());
  }
}
