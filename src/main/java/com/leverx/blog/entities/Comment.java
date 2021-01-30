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

/** @author Andrey Egorov */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 100)
  private String message;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  private User user;
}
