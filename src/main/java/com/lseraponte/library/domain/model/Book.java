package com.lseraponte.library.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "title", "author_id" })
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "author_id", nullable = false)
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Setter(AccessLevel.NONE)
    private Set<Category> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "libraryuser_id")
    private LibraryUser loanedTo;

}
