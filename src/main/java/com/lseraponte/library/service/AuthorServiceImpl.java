package com.lseraponte.library.service;

import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public ResponseEntity addAuthor(Author newAuthor) {
        Author author = authorRepository.findByFirstNameAndLastName(newAuthor.getFirstName(), newAuthor.getLastName());
        if(author == null)
            author = authorRepository.save(newAuthor);
        return new ResponseEntity(author, HttpStatus.CREATED);
    }

}
