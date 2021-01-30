package com.leverx.blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 100)
  private String title;

  @NotBlank
  @Size(min = 10, max = 10000)
  private String text;

  @Enumerated(EnumType.STRING)
  private EStatus status;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate updatedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  private User user;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "article_tags",
      joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags;
}
