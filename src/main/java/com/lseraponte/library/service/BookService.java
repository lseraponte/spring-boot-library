package com.lseraponte.library.service;

import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.BooksDto;
import org.springframework.http.ResponseEntity;

public interface BookService {

    ResponseEntity addBook (BooksDto booksDto);
    ResponseEntity deleteBook (BookAuthorDto deleteBookDto);

}
