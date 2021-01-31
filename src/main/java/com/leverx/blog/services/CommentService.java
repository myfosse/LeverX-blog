package com.leverx.blog.services;

import com.leverx.blog.payload.request.entities.CommentRequest;
import com.leverx.blog.payload.response.entities.CommentResponse;

import java.util.List;

/** @author Andrey Egorov */
public interface CommentService {

  CommentResponse save(final CommentRequest commentRequest);

  CommentResponse update(final CommentRequest commentRequest);

  CommentResponse findById(final Long id);

  void deleteById(final Long id);

  List<CommentResponse> getAllByArticleID(final Long articleID);
}
