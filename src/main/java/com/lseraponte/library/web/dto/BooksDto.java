package com.lseraponte.library.web.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BooksDto {

    Set<FullBookDto> books;

}
