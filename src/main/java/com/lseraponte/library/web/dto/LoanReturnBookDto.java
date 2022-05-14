package com.lseraponte.library.web.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class LoanReturnBookDto {

    @NonNull
    String user;

    @NonNull
    Set<BookAuthorDto> books;

}
