package com.leverx.blog.converters;

import com.leverx.blog.entities.Comment;
import com.leverx.blog.payload.request.entities.CommentRequest;
import com.leverx.blog.payload.response.entities.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

/** @author Andrey Egorov */
public class CommentConverter {

  public static Comment convertRequestToEntity(final CommentRequest commentRequest) {
    return Comment.builder()
        .id(commentRequest.getId())
        .message(commentRequest.getMessage())
        .build();
  }

  public static CommentResponse convertEntityToResponse(final Comment comment) {
    return CommentResponse.builder()
        .id(comment.getId())
        .message(comment.getMessage())
        .createdAt(comment.getCreatedAt())
        .articleResponse(ArticleConverter.convertEntityToResponse(comment.getArticle()))
        .userResponse(UserConverter.convertEntityToResponse(comment.getUser()))
        .build();
  }

  public static List<CommentResponse> convertListOfEntityToRequest(final List<Comment> commentList) {
    return commentList.stream()
        .map(CommentConverter::convertEntityToResponse)
        .collect(Collectors.toList());
  }
}
