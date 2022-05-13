package com.lseraponte.library.web.controller;

import com.lseraponte.library.service.BookService;
import com.lseraponte.library.service.LibraryUserService;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.BooksDto;
import com.lseraponte.library.web.dto.LoanReturnBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity addBook(@Valid @RequestBody BooksDto booksDto) {
        return bookService.addBook(booksDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBook(@Valid @RequestBody BookAuthorDto bookAuthorDto) {
        return bookService.deleteBook(bookAuthorDto);
    }

    @PostMapping("/loan")
    public ResponseEntity loanBook(@Valid @RequestBody LoanReturnBookDto loanReturnBookDto) {
        return libraryUserService.loanBook(loanReturnBookDto);
    }

    @PostMapping("/return")
    public ResponseEntity returnBook(@Valid @RequestBody LoanReturnBookDto loanReturnBookDto) {
        return libraryUserService.returnBook(loanReturnBookDto);
    }

}
