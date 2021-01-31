package com.leverx.blog.controllers;
import com.leverx.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(final TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping("/tags-cloud")
  public @ResponseBody ResponseEntity<?> getRatingForTags() {
    return new ResponseEntity<>(tagService.getTagsByRating(), HttpStatus.OK);
  }
}
