package com.leverx.blog.repositories;

import com.leverx.blog.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Andrey Egorov
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
