package com.leverx.blog.controllers;

import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.entities.EStatus;
import com.leverx.blog.payload.response.MessageResponse;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

  private final ArticleService articleService;

  @Autowired
  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @PostMapping("/")
  public @ResponseBody ResponseEntity<?> addArticle() {

    return new ResponseEntity<>(articleService.getAllByStatus(EStatus.PUBLIC), HttpStatus.OK);
  }
}
