package com.leverx.blog.repositories;

import com.leverx.blog.entities.Tag;
import com.leverx.blog.entities.TagRatingView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** @author Andrey Egorov */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByName(final String name);

  @Query(
      value =
          "select t.id, t.name, count(a.article_id) as post_count from tags t"
              + " left join article_tags a on t.id = a.tag_id group by name order by count(tag_id) DESC",
      nativeQuery = true)
  List<TagRatingView> getTagsByRating();
}
