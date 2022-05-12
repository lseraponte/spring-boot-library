package com.lseraponte.library.service;

import com.lseraponte.library.web.dto.LoanReturnBookDto;

public interface LibraryUserService {

    String loanBook (LoanReturnBookDto loanReturnBookDto);

    String returnBook (LoanReturnBookDto loanReturnBookDto);

}
