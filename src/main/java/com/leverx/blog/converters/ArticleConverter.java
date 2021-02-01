package com.leverx.blog.converters;

import com.leverx.blog.entities.Article;
import com.leverx.blog.payload.request.entities.ArticleRequest;
import com.leverx.blog.payload.response.entities.ArticleResponse;

import java.util.List;
import java.util.stream.Collectors;

/** @author Andrey Egorov */
public class ArticleConverter {

  public static Article convertRequestToEntity(final ArticleRequest articleRequest) {
    return Article.builder()
        .id(articleRequest.getId())
        .title(articleRequest.getTitle())
        .text(articleRequest.getText())
        .status(articleRequest.getStatus())
        .tags(TagConverter.convertSetOfRequestToEntity(articleRequest.getTags()))
        .build();
  }

  public static ArticleResponse convertEntityToResponse(final Article article) {
    return ArticleResponse.builder()
        .id(article.getId())
        .title(article.getTitle())
        .text(article.getText())
        .status(article.getStatus())
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt())
        .userResponse(UserConverter.convertEntityToResponse(article.getUser()))
        .tags(TagConverter.convertSetOfEntityToResponse(article.getTags()))
        .build();
  }

  public static List<ArticleResponse> convertListOfEntityToRequest(final List<Article> articleList) {
    return articleList.stream()
        .map(ArticleConverter::convertEntityToResponse)
        .collect(Collectors.toList());
  }
}
