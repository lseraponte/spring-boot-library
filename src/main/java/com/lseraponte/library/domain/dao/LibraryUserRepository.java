package com.lseraponte.library.domain.dao;

import com.lseraponte.library.domain.model.LibraryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends CrudRepository<LibraryUser, Long> {

    @Override
    LibraryUser save(LibraryUser entity);

    LibraryUser findByUsername(String username);

}
