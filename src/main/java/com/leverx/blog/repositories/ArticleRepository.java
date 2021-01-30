package com.leverx.blog.repositories;

import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @author Andrey Egorov */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> getAllByStatus(EStatus status);
}
