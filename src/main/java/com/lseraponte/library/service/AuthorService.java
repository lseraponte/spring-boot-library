package com.lseraponte.library.service;

import com.lseraponte.library.domain.model.Author;
import org.springframework.http.ResponseEntity;

public interface AuthorService {

    ResponseEntity addAuthor(Author author);

}
