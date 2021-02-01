package com.leverx.blog.controllers;

import com.leverx.blog.payload.request.entities.CommentRequest;
import com.leverx.blog.payload.response.MessageResponse;
import com.leverx.blog.services.CommentService;
import com.leverx.blog.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** @author Andrey Egorov */
@RestController
@RequestMapping("/api/v1/articles")
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(final CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/{id}/comments")
  public @ResponseBody ResponseEntity<?> getAllCommentsForArticle(@PathVariable final Long id) {

    return new ResponseEntity<>(commentService.getAllByArticleID(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/comments/{commentID}")
  public @ResponseBody ResponseEntity<?> getCommentByID(
      @PathVariable final Long id, @PathVariable final Long commentID) {
    return new ResponseEntity<>(commentService.findById(commentID), HttpStatus.OK);
  }

  @PostMapping("/{id}/comments")
  public @ResponseBody ResponseEntity<?> addComment(
      @PathVariable final Long id, @Valid @RequestBody final CommentRequest commentRequest) {

    commentRequest.setUserID(getAuthenticationUserID());
    commentRequest.setArticleID(id);

    return new ResponseEntity<>(commentService.save(commentRequest), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}/comments/{commentID}")
  public @ResponseBody ResponseEntity<?> deleteCommentById(
      @PathVariable final Long id, @PathVariable final Long commentID) {

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
