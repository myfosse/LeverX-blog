package com.leverx.blog.repositories;

import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.EStatus;
import com.leverx.blog.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/** @author Andrey Egorov */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> getAllByStatus(final EStatus status);

    List<Article> getAllByUserId(final Long userID);

    List<Article> getAllByTagsInAndStatus(Set<Tag> tags, EStatus status);
}
