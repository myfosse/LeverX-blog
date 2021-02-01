package com.leverx.blog.controller;
/*
import com.leverx.blog.payload.response.TagRatingResponse;
import com.leverx.blog.services.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
*/
/** @author Andrey Egorov *//*
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TagControllerTest {

  private final String path = "/api/v1/tags";

  @Autowired private MockMvc mockMvc;

  @MockBean private TagService tagService;

  @BeforeEach
  public void setUp() {
    when(tagService.getTagsByRating())
        .thenReturn(Collections.singletonList(new TagRatingResponse(1L, "name", 1)));
  }

  @Test
  public void test_GetRatingForTags() throws Exception {
    mockMvc
        .perform(get(path + "/tags-cloud"))
        .andDo(print())
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("name")))
        .andExpect(jsonPath("$.postCount", is(1L)));
  }
}*/
