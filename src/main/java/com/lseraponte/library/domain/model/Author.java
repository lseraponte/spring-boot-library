package com.lseraponte.library.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "firstName", "lastName" })
})
@NoArgsConstructor
@RequiredArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Setter @Getter @NonNull
    private String firstName;

    @Setter @Getter @NonNull
    private String lastName;

}
