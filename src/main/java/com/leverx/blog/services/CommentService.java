package com.leverx.blog.services;

import com.leverx.blog.payload.request.entities.CommentRequest;
import com.leverx.blog.payload.response.entities.CommentResponse;

import java.util.List;

/** @author Andrey Egorov */
public interface CommentService {

  CommentResponse save(CommentRequest commentRequest);

  CommentResponse update(CommentRequest commentRequest);

  CommentResponse findById(Long id);

  void deleteById(Long id);

  List<CommentResponse> getAllByArticleID(Long articleID);
}
