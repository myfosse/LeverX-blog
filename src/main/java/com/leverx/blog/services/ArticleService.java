package com.leverx.blog.services;

import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.request.entities.TagRequest;
import com.leverx.blog.payload.response.entities.ArticleResponse;

import java.util.List;
import java.util.Set;

/**
 * @author Andrey Egorov
 */
public interface ArticleService {

    ArticleResponse save(ArticleRequest articleRequest);

    ArticleResponse update(ArticleRequest articleRequest);

    ArticleResponse findById(Long id);

    void deleteById(Long id);

    List<ArticleResponse> getAllPublicArticles();

    List<ArticleResponse> getAllByUserId(Long userID);

    List<ArticleResponse> getAllPublicArticlesByTagsIn(Set<TagRequest> tagList);
}
