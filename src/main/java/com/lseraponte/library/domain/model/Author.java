package com.lseraponte.library.domain.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "author")
    private Set<Book> wroteBooks;

}
