package com.lseraponte.library.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class LibraryUser {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(unique=true)
    private String username;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loanedTo")
    @Setter(AccessLevel.NONE)
    private List<Book> loanedBooks;

}
