package com.lseraponte.library.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(unique=true)
    @Setter @Getter @NonNull
    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loanedTo")
    @Getter
    private Set<Book> loanedBooks = new HashSet<>();

}
