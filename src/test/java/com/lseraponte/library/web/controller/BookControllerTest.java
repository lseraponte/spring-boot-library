package com.lseraponte.library.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.service.BookService;
import com.lseraponte.library.service.LibraryUserService;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.BooksDto;
import com.lseraponte.library.web.dto.LoanReturnBookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService bookService;

    @Mock
    LibraryUserService libraryUserService;

    @Test
    void addBook() throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("AddBook.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> booksResponse = objectMapper.readValue(inputStream, new TypeReference<List<Book>>(){});

        Mockito.when(bookService.addBook(Mockito.any(BooksDto.class))).thenReturn(new ResponseEntity(booksResponse, HttpStatus.CREATED));

        ResponseEntity response = bookService.addBook(new BooksDto());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(booksResponse, response.getBody());

    }

    @Test
    void deleteBook() {

        String bookDeletedResponse = "Harry Potter and the Half-Blood Prince deleted.";

        Mockito.when(bookService.deleteBook(Mockito.any(BookAuthorDto.class))).thenReturn(new ResponseEntity(bookDeletedResponse, HttpStatus.OK));

        ResponseEntity response = bookService.deleteBook(new BookAuthorDto());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(bookDeletedResponse, response.getBody());

    }

    @Test
    void loanBook() {

        String bookLoanedResponse = "The Book 'Harry Potter and the Half-Blood Prince' wrote by" +
                "J.K. Rowling has been loaned to User One.";

        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        Set<BookAuthorDto> books = new HashSet<>();
        books.add(new BookAuthorDto());

        Mockito.when(libraryUserService.loanBook(Mockito.any(LoanReturnBookDto.class)))
                .thenReturn(new ResponseEntity(bookLoanedResponse, HttpStatus.CREATED));

        ResponseEntity response = libraryUserService.loanBook(new LoanReturnBookDto("User One", books));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(bookLoanedResponse, response.getBody());

    }

    @Test
    void returnBook() {

        String bookReturnedResponse = "The Book 'Harry Potter and the Half-Blood Prince' wrote by" +
                "J.K. Rowling has been returned to the Library from User One.";

        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        Set<BookAuthorDto> books = new HashSet<>();
        books.add(new BookAuthorDto());

        Mockito.when(libraryUserService.returnBook(Mockito.any(LoanReturnBookDto.class)))
                .thenReturn(new ResponseEntity(bookReturnedResponse, HttpStatus.OK));

        ResponseEntity response = libraryUserService.returnBook(new LoanReturnBookDto("User One", books));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(bookReturnedResponse, response.getBody());

    }
}