package com.lseraponte.library.web.dto;

import com.lseraponte.library.domain.model.Author;
import lombok.Data;

@Data
public class BookAuthorDto {

    String title;

    Author author;

}
