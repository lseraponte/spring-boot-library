package com.lseraponte.library.domain.dao;

import com.lseraponte.library.domain.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Override
    Author save(Author entity);

    Author findById(long id);

    Author findByFirstNameAndLastName(String firstName, String lastName);

}
