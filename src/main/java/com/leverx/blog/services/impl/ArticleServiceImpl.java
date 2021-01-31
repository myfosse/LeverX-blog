package com.leverx.blog.services.impl;

import com.leverx.blog.converters.ArticleConverter;
import com.leverx.blog.converters.TagConverter;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.EStatus;
import com.leverx.blog.entities.Tag;
import com.leverx.blog.entities.User;
import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.payload.response.entities.ArticleResponse;
import com.leverx.blog.repositories.ArticleRepository;
import com.leverx.blog.repositories.TagRepository;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// TODO: find tags before add and update
/** @author Andrey Egorov */
@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final TagRepository tagRepository;
  private final UserRepository userRepository;

  @Autowired
  public ArticleServiceImpl(
      ArticleRepository articleRepository,
      TagRepository tagRepository,
      UserRepository userRepository) {
    this.articleRepository = articleRepository;
    this.tagRepository = tagRepository;
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

    Set<Tag> tags = new HashSet<>();

    for (TagRequest tagRequest : articleRequest.getTags()) {
      Optional<Tag> tag = tagRepository.findByName(tagRequest.getName());
      if (tag.isPresent()) {
        tags.add(tag.get());
      } else {
        tags.add(TagConverter.convertRequestToEntity(tagRequest));
      }
    }
    article.setTags(tags);

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

    Set<Tag> tags = new HashSet<>();

    for (TagRequest tagRequest : articleRequest.getTags()) {
      Optional<Tag> tag = tagRepository.findByName(tagRequest.getName());
      if (tag.isPresent()) {
        tags.add(tag.get());
      } else {
        tags.add(TagConverter.convertRequestToEntity(tagRequest));
      }
    }
    article.setTags(tags);

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

  @Override
  public List<ArticleResponse> getAllByUserId(Long userID) {
    return ArticleConverter.convertListOfEntityToRequest(articleRepository.getAllByUserId(userID));
  }
}
