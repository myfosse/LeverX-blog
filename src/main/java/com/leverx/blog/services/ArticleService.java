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

    ArticleResponse save(final ArticleRequest articleRequest);

    ArticleResponse update(final ArticleRequest articleRequest);

    ArticleResponse findById(final Long id);

    void deleteById(final Long id);

    List<ArticleResponse> getAllPublicArticles();

    List<ArticleResponse> getAllByUserId(final Long userID);

    List<ArticleResponse> getAllPublicArticlesByTagsIn(final Set<TagRequest> tagList);
}
