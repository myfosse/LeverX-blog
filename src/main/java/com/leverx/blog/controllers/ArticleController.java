package com.leverx.blog.controllers;

import com.leverx.blog.entities.EStatus;
import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.response.MessageResponse;
import com.leverx.blog.services.ArticleService;
import com.leverx.blog.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1")
public class ArticleController {

  private final ArticleService articleService;

  @Autowired
  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @PostMapping("/articles")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public @ResponseBody ResponseEntity<?> addArticle(
      @Valid @RequestBody final ArticleRequest articleRequest) {
    articleRequest.setUserID(getAuthenticationUserID());
    return new ResponseEntity<>(articleService.save(articleRequest), HttpStatus.CREATED);
  }

  @GetMapping("/articles/my")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public @ResponseBody ResponseEntity<?> getUserArticles() {
    return new ResponseEntity<>(
        articleService.getAllByUserId(getAuthenticationUserID()), HttpStatus.OK);
  }

  @GetMapping("/articles")
  public @ResponseBody ResponseEntity<?> getAllPublicArticles() {
    return new ResponseEntity<>(articleService.getAllByStatus(EStatus.PUBLIC), HttpStatus.OK);
  }

  @PutMapping("/articles/{id}")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public @ResponseBody ResponseEntity<?> updateArticle(
      @PathVariable Long id, @Valid @RequestBody final ArticleRequest articleRequest) {

    if (id < 0
        || !articleService
            .findById(id)
            .getUserResponse()
            .getId()
            .equals(getAuthenticationUserID())) {
      return new ResponseEntity<>(
          new MessageResponse("No such article for this user"), HttpStatus.BAD_REQUEST);
    }

    articleRequest.setUserID(getAuthenticationUserID());
    articleRequest.setId(id);

    return new ResponseEntity<>(articleService.update(articleRequest), HttpStatus.OK);
  }

  @DeleteMapping("/articles/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_USER')")
  public @ResponseBody ResponseEntity<?> deleteArticle(
          @PathVariable Long id) {

    if (id < 0
            || !articleService
            .findById(id)
            .getUserResponse()
            .getId()
            .equals(getAuthenticationUserID())) {
      return new ResponseEntity<>(
              new MessageResponse("No such article for this user"), HttpStatus.BAD_REQUEST);
    }

    articleService.deleteById(id);

    return new ResponseEntity<>(new MessageResponse("Deleted"), HttpStatus.ACCEPTED);
  }

  public Long getAuthenticationUserID() {
    return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        ? ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getId()
        : 0;
  }
}
