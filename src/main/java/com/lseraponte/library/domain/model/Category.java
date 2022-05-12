package com.lseraponte.library.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(unique=true)
    @Getter @Setter
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    @Getter
    @JsonIgnore
    private Set<Book> booksBelonging = new HashSet<>();

}
