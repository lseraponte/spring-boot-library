package com.lseraponte.library.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.service.AuthorService;
import com.lseraponte.library.web.dto.BooksDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    AuthorService authorService;

    @Test
    public void test_addAuthor() throws Exception {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("AddAuthor.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Author authorResponse = objectMapper.readValue(inputStream, Author.class);

        Mockito.when(authorService.addAuthor(Mockito.any(Author.class))).thenReturn(new ResponseEntity(authorResponse, HttpStatus.CREATED));

        ResponseEntity response = authorService.addAuthor(authorResponse);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(authorResponse, response.getBody());

    }

}