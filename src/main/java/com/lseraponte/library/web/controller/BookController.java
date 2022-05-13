package com.lseraponte.library.web.controller;

import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.service.BookService;
import com.lseraponte.library.service.LibraryUserService;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.BooksDto;
import com.lseraponte.library.web.dto.LoanReturnBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    LibraryUserService libraryUserService;

    @PostMapping("/add")
    public List<Book> addBook(@Valid @RequestBody BooksDto booksDto) {
        return bookService.addBook(booksDto);
    }

    @DeleteMapping("/delete")
    public String deleteBook(@Valid @RequestBody BookAuthorDto bookAuthorDto) {
        return bookService.deleteBook(bookAuthorDto);
    }

    @PostMapping("/loan")
    public String loanBook(@Valid @RequestBody LoanReturnBookDto loanReturnBookDto) {
        return libraryUserService.loanBook(loanReturnBookDto);
    }

    @PostMapping("/return")
    public String returnBook(@Valid @RequestBody LoanReturnBookDto loanReturnBookDto) {
        return libraryUserService.returnBook(loanReturnBookDto);
    }

}
