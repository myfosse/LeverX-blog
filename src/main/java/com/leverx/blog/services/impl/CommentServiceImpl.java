package com.leverx.blog.services.impl;

import com.leverx.blog.converters.CommentConverter;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.Comment;
import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.CommentRequest;
import com.leverx.blog.payload.response.entities.CommentResponse;
import com.leverx.blog.repositories.ArticleRepository;
import com.leverx.blog.repositories.CommentRepository;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/** @author Andrey Egorov */
@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

  @Autowired
  public CommentServiceImpl(
      CommentRepository commentRepository,
      ArticleRepository articleRepository,
      UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
  }

  @Override
  public CommentResponse save(final CommentRequest commentRequest) {

    User user =
        userRepository
            .findById(commentRequest.getUserID())
            .orElseThrow(EntityNotFoundException::new);

    Article article =
        articleRepository
            .findById(commentRequest.getArticleID())
            .orElseThrow(EntityNotFoundException::new);

    Comment comment = CommentConverter.convertRequestToEntity(commentRequest);
    comment.setArticle(article);
    comment.setUser(user);
    comment.setCreatedAt(LocalDate.now());

    return CommentConverter.convertEntityToResponse(commentRepository.save(comment));
  }

  @Override
  public CommentResponse findById(final Long id) {
    return CommentConverter.convertEntityToResponse(
        commentRepository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public void deleteById(final Long id) {
    commentRepository.deleteById(id);
  }

  @Override
  public List<CommentResponse> getAllByArticleID(final Long articleId) {
    return CommentConverter.convertListOfEntityToRequest(
        commentRepository.getAllByArticleId(articleId));
  }
}
