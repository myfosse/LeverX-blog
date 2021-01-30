package com.leverx.blog.services;

import com.leverx.blog.entities.EStatus;
import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.response.entities.ArticleResponse;

import java.util.List;

/**
 * @author Andrey Egorov
 */
public interface ArticleService {

    ArticleResponse save(ArticleRequest articleRequest);

    ArticleResponse update(ArticleRequest articleRequest);

    ArticleResponse findById(Long id);

    void deleteById(Long id);

    List<ArticleResponse> getAllByStatus(EStatus status);
}
