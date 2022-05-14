package com.lseraponte.library.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(unique=true)
    @Getter @Setter @NonNull
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    @Getter
    @JsonIgnore
    private Set<Book> booksBelonging = new HashSet<>();

}
