package com.lseraponte.library.service;

import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.dao.BookRepository;
import com.lseraponte.library.domain.dao.CategoryRepository;
import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.domain.model.Category;
import com.lseraponte.library.web.dto.BooksDto;
import com.lseraponte.library.web.dto.FullBookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
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

    @Test
    void addBook() {

        Book book = new Book();
        Author author = new Author("J.K.", "Rowling");

        BooksDto booksDto = new BooksDto();
        FullBookDto fullBookDto = new FullBookDto();
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setCategoryName("Fantasy");
        categories.add(category);

        fullBookDto.setTitle("Harry Potter and the Half-Blood Prince");
        fullBookDto.setAuthor(author);
        fullBookDto.setCategories(categories);

        Set<FullBookDto> books = new HashSet<>();
        books.add(fullBookDto);

        booksDto.setBooks(books);

        BDDMockito.given(authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName())).willReturn(null);
        BDDMockito.given(categoryRepository.findByCategoryName(category.getCategoryName())).willReturn(null);
        BDDMockito.given(bookRepository.findByTitleAndAuthor_id("Harry Potter and the Half-Blood Prince", 0)).willReturn(null);
        BDDMockito.given(bookRepository.save(book)).willReturn(book);

        ResponseEntity responseEntity = bookService.addBook(booksDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    void deleteBook() {
    }
}