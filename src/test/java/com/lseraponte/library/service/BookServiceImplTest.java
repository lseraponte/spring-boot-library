package com.lseraponte.library.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.dao.BookRepository;
import com.lseraponte.library.domain.dao.CategoryRepository;
import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.domain.model.Category;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.BooksDto;
import com.lseraponte.library.web.dto.FullBookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock(lenient = true)
    BookRepository bookRepository;

    @Mock(lenient = true)
    AuthorRepository authorRepository;

    @Mock(lenient = true)
    CategoryRepository categoryRepository;

    @InjectMocks
    BookServiceImpl bookService;

    Book book;
    Author author;
    Category category;
    Set<Category> categories = new HashSet<>();
    List<Book> booksResponse;

    @BeforeEach
    public void init() throws IOException {
        book = new Book();
        author = new Author("J.K.", "Rowling");
        book.setTitle("Harry Potter and the Half-Blood Prince");
        book.setAuthor(author);
        category = new Category("Fantasy");
        categories.add(category);

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("AddBook.json");
        ObjectMapper objectMapper = new ObjectMapper();
        booksResponse = objectMapper.readValue(inputStream, new TypeReference<List<Book>>(){});
    }

    @Test
    void addBook() throws IOException {

        BooksDto booksDto = new BooksDto();
        FullBookDto fullBookDto = new FullBookDto();

        fullBookDto.setTitle(book.getTitle());
        fullBookDto.setAuthor(book.getAuthor());
        fullBookDto.setCategories(categories);

        Set<FullBookDto> books = new HashSet<>();
        books.add(fullBookDto);
        booksDto.setBooks(books);

        BDDMockito.given(authorRepository.findByFirstNameAndLastName(book.getAuthor().getFirstName(),
                book.getAuthor().getLastName())).willReturn(null);
        BDDMockito.given(categoryRepository.findByCategoryName(category.getCategoryName())).willReturn(null);
        BDDMockito.given(bookRepository.findByTitleAndAuthor_id(fullBookDto.getTitle(), author.getId())).willReturn(null);
        BDDMockito.given(bookRepository.save(book)).willReturn(book);

        ResponseEntity responseEntity = bookService.addBook(booksDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(booksResponse, responseEntity.getBody());

    }

    @Test
    void deleteBook() {

        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        bookAuthorDto.setTitle(book.getTitle());
        bookAuthorDto.setAuthor(author);

        BDDMockito.given(authorRepository.findByFirstNameAndLastName(book.getAuthor().getFirstName(),
                book.getAuthor().getLastName())).willReturn(author);
        BDDMockito.given(bookRepository.findByTitleAndAuthor_id(book.getTitle(), author.getId())).willReturn(book);

        ResponseEntity responseEntity = bookService.deleteBook(bookAuthorDto);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Harry Potter and the Half-Blood Prince deleted.", responseEntity.getBody());

    }
}