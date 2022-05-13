package com.lseraponte.library.service;

import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.BooksDto;

import java.util.List;

public interface BookService {

    List<Book> addBook (BooksDto booksDto);
    String deleteBook (BookAuthorDto deleteBookDto);

}
