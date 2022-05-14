package com.lseraponte.library.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.dao.BookRepository;
import com.lseraponte.library.domain.dao.LibraryUserRepository;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.domain.model.LibraryUser;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.LoanReturnBookDto;
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
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class LibraryUserServiceImplTest {

    @Mock(lenient = true)
    LibraryUserRepository libraryUserRepository;

    @Mock(lenient = true)
    BookRepository bookRepository;

    @Mock(lenient = true)
    AuthorRepository authorRepository;

    @InjectMocks
    LibraryUserServiceImpl libraryUserService;

    LibraryUser user;
    Set<BookAuthorDto> bookAuthorDto;
    LoanReturnBookDto loanReturnBookDto;

    static String FIRST_LOANED = "The Book 'The Lord of the Rings' wrote by J.R.R. Tolkien has been loaned to User One";
    static String SECOND_LOANED = "The Book 'A Tale of Two Cities' wrote by Charles Dickens has been loaned to User One";
    static String THIRD_LOANED = "The Book 'Don Quixote' wrote by Miguel de Cervantes has been loaned to User One";

    static String FIRST_RETURNED = "The Book 'The Lord of the Rings' wrote by J.R.R. Tolkien has been returned to the Library from User One";
    static String SECOND_RETURNED = "The Book 'A Tale of Two Cities' wrote by Charles Dickens has been returned to the Library from User One";
    static String THIRD_RETURNED = "The Book 'Don Quixote' wrote by Miguel de Cervantes has been returned to the Library from User One";

    @BeforeEach
    public void init() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("LoanBooks.json");
        ObjectMapper objectMapper = new ObjectMapper();
        bookAuthorDto = objectMapper.readValue(inputStream, new TypeReference<Set<BookAuthorDto>>(){});

        user = new LibraryUser("User One");
        Set<BookAuthorDto> books;
        loanReturnBookDto = new LoanReturnBookDto(user.getUsername(), bookAuthorDto);
    }

    @Test
    void loanBook() {

        BDDMockito.given(libraryUserRepository.findByUsername(user.getUsername())).willReturn(user);
        for (BookAuthorDto currentBookDto : bookAuthorDto) {
            Book currentBook = new Book();
            currentBook.setAuthor(currentBookDto.getAuthor());
            currentBook.setTitle(currentBookDto.getTitle());

            BDDMockito.given(authorRepository.findByFirstNameAndLastName(currentBookDto.getAuthor().getFirstName(), 
                    currentBookDto.getAuthor().getLastName())).willReturn(currentBookDto.getAuthor());
            BDDMockito.given(bookRepository.findByTitleAndAuthor_id(currentBookDto.getTitle(), currentBookDto.getAuthor().getId()))
                    .willReturn(currentBook);
        }

        ResponseEntity responseEntity = libraryUserService.loanBook(loanReturnBookDto);
        String responseString = responseEntity.getBody().toString();

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertTrue(responseString.contains(FIRST_LOANED));
        Assertions.assertTrue(responseString.contains(SECOND_LOANED));
        Assertions.assertTrue(responseString.contains(THIRD_LOANED));

    }

    @Test
    void returnBook() {


        BDDMockito.given(libraryUserRepository.findByUsername(user.getUsername())).willReturn(user);
        for (BookAuthorDto currentBookDto : bookAuthorDto) {
            Book currentBook = new Book();
            currentBook.setAuthor(currentBookDto.getAuthor());
            currentBook.setTitle(currentBookDto.getTitle());
            currentBook.setLoanedTo(user);
            user.getLoanedBooks().add(currentBook);

            BDDMockito.given(authorRepository.findByFirstNameAndLastName(currentBookDto.getAuthor().getFirstName(),
                    currentBookDto.getAuthor().getLastName())).willReturn(currentBookDto.getAuthor());
            BDDMockito.given(bookRepository.findByTitleAndAuthor_id(currentBookDto.getTitle(), currentBookDto.getAuthor().getId()))
                    .willReturn(currentBook);
        }

        ResponseEntity responseEntity = libraryUserService.returnBook(loanReturnBookDto);
        String responseString = responseEntity.getBody().toString();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseString.contains(FIRST_RETURNED));
        Assertions.assertTrue(responseString.contains(SECOND_RETURNED));
        Assertions.assertTrue(responseString.contains(THIRD_RETURNED));

    }
}