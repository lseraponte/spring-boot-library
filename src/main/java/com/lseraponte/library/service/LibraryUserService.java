package com.lseraponte.library.service;

import com.lseraponte.library.web.dto.LoanReturnBookDto;
import org.springframework.http.ResponseEntity;

public interface LibraryUserService {

    ResponseEntity loanBook (LoanReturnBookDto loanReturnBookDto);

    ResponseEntity returnBook (LoanReturnBookDto loanReturnBookDto);

}
