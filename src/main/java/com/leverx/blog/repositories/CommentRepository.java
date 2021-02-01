package com.leverx.blog.repositories;

import com.leverx.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andrey Egorov
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByArticleId(final Long articleID);
}
