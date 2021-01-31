package com.leverx.blog.services.impl;

import com.leverx.blog.converters.TagConverter;
import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.payload.response.TagRatingResponse;
import com.leverx.blog.payload.response.entities.TagResponse;
import com.leverx.blog.repositories.TagRepository;
import com.leverx.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/** @author Andrey Egorov */
@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Autowired
  public TagServiceImpl(final TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @Override
  public TagResponse save(final TagRequest tagRequest) {
    return TagConverter.convertEntityToResponse(
        tagRepository.save(TagConverter.convertRequestToEntity(tagRequest)));
  }

  @Override
  public TagResponse findById(final Long id) {
    return TagConverter.convertEntityToResponse(
        tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public TagResponse findByName(final String name) {
    return TagConverter.convertEntityToResponse(
        tagRepository.findByName(name).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public List<TagRatingResponse> getTagsByRating() {
    return tagRepository.getTagsByRating().stream()
        .map(
            t ->
                TagRatingResponse.builder()
                    .id(t.getId())
                    .name(t.getName())
                    .postCount(t.getPostCount())
                    .build())
        .collect(Collectors.toList());
  }
}
