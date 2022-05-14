package com.lseraponte.library.web.dto;

import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.domain.model.Category;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class FullBookDto {

    @NotNull
    private String title;

    @NotNull
    private Author author;

    @NotNull
    private Set<Category> categories;

}
