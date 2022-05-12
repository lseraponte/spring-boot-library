package com.lseraponte.library.domain.dao;

import com.lseraponte.library.domain.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Override
    Category save(Category entity);

    Category findByCategoryName(String name);

}
