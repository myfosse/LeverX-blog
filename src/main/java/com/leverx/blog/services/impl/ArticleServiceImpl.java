package com.leverx.blog.services.impl;

import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.EStatus;
import com.leverx.blog.repositories.ArticleRepository;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @author Andrey Egorov */
@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Override
  public Article save(Article article) {
    return articleRepository.save(article);
  }

  @Override
  public ArticleDTO update(ArticleDTO articleDTO) {
    return null;
  }

  @Override
  public Article findById(Long id) {
    return articleRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    articleRepository.deleteById(id);
  }

  @Override
  public List<Article> getAllByStatus(EStatus status) {
    return articleRepository.getAllByStatus(status);
  }
}
