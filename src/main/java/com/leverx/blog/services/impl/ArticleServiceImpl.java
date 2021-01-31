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
import java.util.*;

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
  public ArticleResponse save(final ArticleRequest articleRequest) {

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
        tags.add(tagRepository.save(TagConverter.convertRequestToEntity(tagRequest)));
      }
    }
    article.setTags(tags);

    return ArticleConverter.convertEntityToResponse(articleRepository.save(article));
  }

  @Override
  public ArticleResponse update(final ArticleRequest articleRequest) {

    User user =
        userRepository
            .findById(articleRequest.getUserID())
            .orElseThrow(EntityNotFoundException::new);

    Article article = ArticleConverter.convertRequestToEntity(articleRequest);
    article.setUpdatedAt(LocalDate.now());
    article.setCreatedAt(articleRepository.findById(articleRequest.getId()).get().getCreatedAt());
    article.setUser(user);
    article.setId(articleRequest.getId());

    Set<Tag> tags = new HashSet<>();

    for (TagRequest tagRequest : articleRequest.getTags()) {
      Optional<Tag> tag = tagRepository.findByName(tagRequest.getName());
      if (tag.isPresent()) {
        tags.add(tag.get());
      } else {
        tags.add(tagRepository.save(TagConverter.convertRequestToEntity(tagRequest)));
      }
    }
    article.setTags(tags);

    return ArticleConverter.convertEntityToResponse(articleRepository.save(article));
  }

  @Override
  public ArticleResponse findById(final Long id) {
    return ArticleConverter.convertEntityToResponse(
        articleRepository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public void deleteById(final Long id) {
    articleRepository.deleteById(id);
  }

  @Override
  public List<ArticleResponse> getAllPublicArticles() {
    return ArticleConverter.convertListOfEntityToRequest(
        articleRepository.getAllByStatus(EStatus.PUBLIC));
  }

  @Override
  public List<ArticleResponse> getAllByUserId(final Long userId) {
    return ArticleConverter.convertListOfEntityToRequest(articleRepository.getAllByUserId(userId));
  }

  @Override
  public List<ArticleResponse> getAllPublicArticlesByTagsIn(final Set<TagRequest> tagList) {

    Set<Tag> tags = new HashSet<>();

    for (TagRequest tagRequest : tagList) {
      Optional<Tag> tag = tagRepository.findByName(tagRequest.getName());
      tag.ifPresent(tags::add);
    }

    return ArticleConverter.convertListOfEntityToRequest(
        articleRepository.getAllByTagsInAndStatus(tags, EStatus.PUBLIC));
  }
}
