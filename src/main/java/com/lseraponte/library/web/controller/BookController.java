package com.lseraponte.library.web.controller;

import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.service.BookService;
import com.lseraponte.library.service.LibraryUserService;
import com.lseraponte.library.web.dto.AddBookDto;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.LoanReturnBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    LibraryUserService libraryUserService;

    @PostMapping("/add")
    public Book addBook(@Valid @RequestBody AddBookDto addBookDto) {
        return bookService.addBook(addBookDto);
    }

    @DeleteMapping("/delete")
    public String deleteBook(@Valid @RequestBody BookAuthorDto bookAuthorDto) {
        return bookService.deleteBook(bookAuthorDto);
    }

    @PostMapping("/loan")
    public String loanBook(@Valid @RequestBody LoanReturnBookDto loanReturnBookDto) {
        return libraryUserService.loanBook(loanReturnBookDto);
    }

}
