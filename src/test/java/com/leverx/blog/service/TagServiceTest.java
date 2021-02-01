package com.leverx.blog.service;

import com.leverx.blog.converters.TagConverter;
import com.leverx.blog.entities.Tag;
import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.repositories.TagRepository;
import com.leverx.blog.services.TagService;
import com.leverx.blog.services.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/** @author Andrey Egorov */
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TagServiceImpl.class})
public class TagServiceTest {

  @Autowired private TagService tagService;

  @MockBean private TagRepository tagRepository;

  Tag actualInput;

  Tag expectedInput;

  List<Tag> actualList;

  List<Tag> expectedList;

  TagRequest tagRequest;


  @BeforeEach
  public void setUp() {
    actualList = Collections.singletonList(new Tag(1L, "Tag"));
    expectedList = Collections.singletonList(new Tag(1L, "Tag"));
    actualInput = actualList.get(0);
    expectedInput = expectedList.get(0);
    tagRequest = TagRequest.builder().name("Tag").build();
  }

  @Test
  public void testSaveEntity() {

    actualInput.setId(null);

    when(tagRepository.save(actualInput)).thenReturn(expectedInput);

    assertEquals(TagConverter.convertEntityToResponse(expectedInput), tagService.save(tagRequest));
  }

  @Test
  public void testFindById() {
    Long id = 1L;

    when(tagRepository.findById(id)).thenReturn(Optional.of(actualInput));

    assertEquals(TagConverter.convertEntityToResponse(expectedInput), tagService.findById(id));
  }


  @Test
  public void testFindByUsername() {
    when(tagRepository.findByName(actualInput.getName())).thenReturn(Optional.of(actualInput));

    assertEquals(
        TagConverter.convertEntityToResponse(expectedInput),
        tagService.findByName(actualInput.getName()));
  }
}
