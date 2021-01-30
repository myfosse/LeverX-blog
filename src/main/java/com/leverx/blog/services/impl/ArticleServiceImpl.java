package com.leverx.blog.services.impl;

import com.leverx.blog.converters.ArticleConverter;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.EStatus;
import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.response.entities.ArticleResponse;
import com.leverx.blog.repositories.ArticleRepository;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

//TODO: find tags before add and update
/** @author Andrey Egorov */
@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
  }

  @Override
  public ArticleResponse save(ArticleRequest articleRequest) {

    User user =
        userRepository
            .findById(articleRequest.getUserID())
            .orElseThrow(EntityNotFoundException::new);

    Article article = ArticleConverter.convertRequestToEntity(articleRequest);
    article.setCreatedAt(LocalDate.now());
    article.setUser(user);

    return ArticleConverter.convertEntityToResponse(articleRepository.save(article));
  }

  @Override
  public ArticleResponse update(ArticleRequest articleRequest) {

    User user =
        userRepository
            .findById(articleRequest.getUserID())
            .orElseThrow(EntityNotFoundException::new);

    Article article = ArticleConverter.convertRequestToEntity(articleRequest);
    article.setUpdatedAt(LocalDate.now());
    article.setUser(user);
    article.setId(articleRequest.getId());

    return ArticleConverter.convertEntityToResponse(articleRepository.save(article));
  }

  @Override
  public ArticleResponse findById(Long id) {
    return ArticleConverter.convertEntityToResponse(
        articleRepository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public void deleteById(Long id) {
    articleRepository.deleteById(id);
  }

  @Override
  public List<ArticleResponse> getAllByStatus(EStatus status) {
    return ArticleConverter.convertListOfEntityToRequest(articleRepository.getAllByStatus(status));
  }
}
