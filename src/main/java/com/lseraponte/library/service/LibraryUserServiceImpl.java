package com.lseraponte.library.service;

import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.dao.BookRepository;
import com.lseraponte.library.domain.dao.LibraryUserRepository;
import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.domain.model.LibraryUser;
import com.lseraponte.library.web.dto.BookAuthorDto;
import com.lseraponte.library.web.dto.LoanReturnBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LibraryUserServiceImpl implements LibraryUserService {

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public String loanBook(LoanReturnBookDto loanReturnBookDto) {

        LibraryUser user = libraryUserRepository.findByUsername(loanReturnBookDto.getUser());
        if (user == null) {
            user = new LibraryUser(loanReturnBookDto.getUser());
        }
        else if (user.getLoanedBooks().size() > 0) {
            return "outstanding loaned book for user " + loanReturnBookDto.getUser();
        }

        if (loanReturnBookDto.getBooks() != null && loanReturnBookDto.getBooks().size() > 3)
            return "Maximum 3 books allowed per loan";

        for (BookAuthorDto bookAuthorDto : loanReturnBookDto.getBooks()) {
            Author author = authorRepository.findByFirstNameAndLastName(bookAuthorDto.getAuthor().getFirstName(),
                    bookAuthorDto.getAuthor().getLastName());

            if (author == null)
                return "No books wrote by " + bookAuthorDto.getAuthor() + "are present in the Library.";

            else {
                Book book = bookRepository.findByTitleAndAuthor_id(bookAuthorDto.getTitle(), author.getId());
                if (book == null)
                    return "No books wrote by " + bookAuthorDto.getAuthor() + "are present in the Library.";
                else if (book.getLoanedTo() != null)
                    return "Book already loaned. Check Library later";

                user.getLoanedBooks().add(book);
                book.setLoanedTo(user);
                bookRepository.save(book);
            }
        }

        libraryUserRepository.save(user);
        return "Loan Completed";

    }

    @Override
    public String returnBook(LoanReturnBookDto loanReturnBookDto) {

        LibraryUser user = libraryUserRepository.findByUsername(loanReturnBookDto.getUser());
        if (user == null)
            return "New user, no books to return";

        else if (user.getLoanedBooks().size() == 0)
            return "No outstanding loaned books for user " + loanReturnBookDto.getUser();

        for (BookAuthorDto bookAuthorDto : loanReturnBookDto.getBooks()) {
            Author author = authorRepository.findByFirstNameAndLastName(bookAuthorDto.getAuthor().getFirstName(),
                    bookAuthorDto.getAuthor().getLastName());

            if (author == null)
                return "The book " + bookAuthorDto.getTitle() + " wrote by " +bookAuthorDto.getAuthor() +
                        "does not belong to the Library.";

            else {
                Book book = bookRepository.findByTitleAndAuthor_id(bookAuthorDto.getTitle(), author.getId());
                if (book == null)
                    return "The book " + bookAuthorDto.getTitle() + " wrote by " +bookAuthorDto.getAuthor() +
                            "does not belong to the Library.";
                else if (book.getLoanedTo() == null || book.getLoanedTo().getId() == user.getId())
                    return "Book not given to " + user.getUsername();

                user.getLoanedBooks().remove(book);
                book.setLoanedTo(null);
                bookRepository.save(book);
            }
        }

        libraryUserRepository.save(user);
        return "Books Returned";

    }
}
