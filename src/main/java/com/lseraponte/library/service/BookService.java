package com.lseraponte.library.service;

import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.web.dto.AddBookDto;
import com.lseraponte.library.web.dto.BookAuthorDto;

public interface BookService {

    Book addBook (AddBookDto book);
    String deleteBook (BookAuthorDto deleteBookDto);

}
