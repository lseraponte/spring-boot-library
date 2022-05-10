package com.lseraponte.library.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(unique=true)
    private String categoryName;
    @ManyToMany(mappedBy = "categories")
    private Set<Book> booksBelonging;

}
