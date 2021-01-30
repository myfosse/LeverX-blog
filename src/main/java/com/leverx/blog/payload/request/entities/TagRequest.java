package com.leverx.blog.payload.request.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Andrey Egorov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
}
