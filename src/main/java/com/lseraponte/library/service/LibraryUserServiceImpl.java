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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity loanBook(LoanReturnBookDto loanReturnBookDto) {

        LibraryUser user = libraryUserRepository.findByUsername(loanReturnBookDto.getUser());
        if (user == null) {
            user = new LibraryUser(loanReturnBookDto.getUser());
        }
        else if (user.getLoanedBooks().size() > 0)
            return new ResponseEntity("Outstanding loaned book for user " + loanReturnBookDto.getUser(), HttpStatus.FORBIDDEN) ;

        if (loanReturnBookDto.getBooks() != null && loanReturnBookDto.getBooks().size() > 3)
            return new ResponseEntity("Maximum 3 books allowed per loan", HttpStatus.FORBIDDEN) ;

        StringBuilder stringBuilder = new StringBuilder();

        for (BookAuthorDto bookAuthorDto : loanReturnBookDto.getBooks()) {
            Author author = authorRepository.findByFirstNameAndLastName(bookAuthorDto.getAuthor().getFirstName(),
                    bookAuthorDto.getAuthor().getLastName());

            if (author == null)
                stringBuilder.append("No books wrote by " + bookAuthorDto.getAuthor().getFirstName() +
                        bookAuthorDto.getAuthor().getLastName() + " are present in the Library. \n");

            else {
                Book book = bookRepository.findByTitleAndAuthor_id(bookAuthorDto.getTitle(), author.getId());
                if (book == null) {
                    stringBuilder.append("The Book '" + bookAuthorDto.getTitle() + "' wrote by " +
                            bookAuthorDto.getAuthor().getFirstName() + bookAuthorDto.getAuthor().getLastName() +
                            " is not present in the Library. \n");
                    continue;
                }
                else if (book.getLoanedTo() != null) {
                    stringBuilder.append("Book already loaned. Check Library later \n");
                    continue;
                }

                user.getLoanedBooks().add(book);
                book.setLoanedTo(user);

                stringBuilder.append("The Book '" + bookAuthorDto.getTitle() + "' wrote by " +
                        bookAuthorDto.getAuthor().getFirstName() + bookAuthorDto.getAuthor().getLastName() +
                        " has been loaned to " + user.getUsername() + "\n");

                bookRepository.save(book);
            }
        }

        libraryUserRepository.save(user);
        return new ResponseEntity(stringBuilder.toString(), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity returnBook(LoanReturnBookDto loanReturnBookDto) {

        LibraryUser user = libraryUserRepository.findByUsername(loanReturnBookDto.getUser());
        if (user == null)
            return new ResponseEntity("New user, no books to return", HttpStatus.OK);

        else if (user.getLoanedBooks().size() == 0)
            return new ResponseEntity("No outstanding loaned books for user " + loanReturnBookDto.getUser(), HttpStatus.OK);

        StringBuilder stringBuilder = new StringBuilder();

        for (BookAuthorDto bookAuthorDto : loanReturnBookDto.getBooks()) {
            Author author = authorRepository.findByFirstNameAndLastName(bookAuthorDto.getAuthor().getFirstName(),
                    bookAuthorDto.getAuthor().getLastName());

            if (author == null)
                stringBuilder.append("No books wrote by " +bookAuthorDto.getAuthor() + " belongs to the Library. \n");

            else {
                Book book = bookRepository.findByTitleAndAuthor_id(bookAuthorDto.getTitle(), author.getId());
                if (book == null) {
                    stringBuilder.append("The book " + bookAuthorDto.getTitle() + " wrote by "
                            + bookAuthorDto.getAuthor() + " does not belong to the Library.");
                    continue;
                }
                else if (book.getLoanedTo() == null || book.getLoanedTo().getId() != user.getId()) {
                    stringBuilder.append("Book not given to " + user.getUsername() + " doesn't need to be returned \n");
                    continue;
                }

                user.getLoanedBooks().remove(book);
                book.setLoanedTo(null);

                stringBuilder.append("The Book '" + bookAuthorDto.getTitle() + "' wrote by " +
                        bookAuthorDto.getAuthor().getFirstName() + bookAuthorDto.getAuthor().getLastName() +
                        " has been returned to the Library from " + user.getUsername() + "\n");

                bookRepository.save(book);
            }
        }

        libraryUserRepository.save(user);
        return new ResponseEntity(stringBuilder.toString(), HttpStatus.OK);

    }
}
