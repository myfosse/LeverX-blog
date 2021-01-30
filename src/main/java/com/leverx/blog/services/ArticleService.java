package com.leverx.blog.services;

import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.EStatus;

import java.util.List;

/**
 * @author Andrey Egorov
 */
public interface ArticleService {

    Article save(Article article);

    ArticleDTO update(ArticleDTO articleDTO);

    Article findById(Long id);

    void deleteById(Long id);

    List<Article> getAllByStatus(EStatus status);
}
