package com.lseraponte.library.domain.dao;

import com.lseraponte.library.domain.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    Book save(Book entity);

    @Override
    void delete(Book entity);

}
