package com.leverx.blog.controllers;

import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.request.entities.CommentRequest;
import com.leverx.blog.payload.response.MessageResponse;
import com.leverx.blog.services.ArticleService;
import com.leverx.blog.services.CommentService;
import com.leverx.blog.services.UserService;
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
@RequestMapping("/api/v1/articles")
public class CommentController {

  private final ArticleService articleService;
  private final CommentService commentService;
  private final UserService userService;

  @Autowired
  public CommentController(
      ArticleService articleService, CommentService commentService, UserService userService) {
    this.articleService = articleService;
    this.commentService = commentService;
    this.userService = userService;
  }

  @GetMapping("/{id}/comments")
  public @ResponseBody ResponseEntity<?> getAllCommentsForArticle(@PathVariable Long id) {

    return new ResponseEntity<>(commentService.getAllByArticleID(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/comments/{commentID}")
  public @ResponseBody ResponseEntity<?> getCommentByID(
      @PathVariable Long id, @PathVariable Long commentID) {
    return new ResponseEntity<>(commentService.findById(commentID), HttpStatus.OK);
  }

  @PostMapping("/{id}/comments")
  @PreAuthorize("hasAnyAuthority('ROLE_USER')")
  public @ResponseBody ResponseEntity<?> addComment(
      @PathVariable Long id, @Valid @RequestBody final CommentRequest commentRequest) {

    commentRequest.setUserID(getAuthenticationUserID());
    commentRequest.setArticleID(id);

    return new ResponseEntity<>(commentService.save(commentRequest), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}/comments/{commentID}")
  public @ResponseBody ResponseEntity<?> deleteCommentById(
      @PathVariable Long id, @PathVariable Long commentID) {

    if (commentService.findById(id).getUserResponse().getId().equals(getAuthenticationUserID())
        || commentService
            .findById(id)
            .getArticleResponse()
            .getUserResponse()
            .getId()
            .equals(getAuthenticationUserID())) {
      commentService.deleteById(commentID);
      return new ResponseEntity<>(new MessageResponse("Sucsecsully deleted"), HttpStatus.OK);
    }
    return new ResponseEntity<>(new MessageResponse("You have no rights"), HttpStatus.BAD_REQUEST);
  }

  public Long getAuthenticationUserID() {
    return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        ? ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getId()
        : 0;
  }
}
