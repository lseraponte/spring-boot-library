package com.lseraponte.library.service;

import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.dao.BookRepository;
import com.lseraponte.library.domain.dao.CategoryRepository;
import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.domain.model.Category;
import com.lseraponte.library.web.dto.BooksDto;
import com.lseraponte.library.web.dto.FullBookDto;
import com.lseraponte.library.web.dto.BookAuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity addBook(BooksDto booksDto) {

        List<Book> savedBooks = new ArrayList<>();

        for (FullBookDto fullBookDto : booksDto.getBooks()) {
            Book book = null;

            Author author = authorRepository.findByFirstNameAndLastName(fullBookDto.getAuthor().getFirstName(),
                    fullBookDto.getAuthor().getLastName());
            if (author != null)
                book = bookRepository.findByTitleAndAuthor_id(fullBookDto.getTitle(), author.getId());

            if (book != null)
                continue;

            else {

                if (author == null)
                    author = new Author(fullBookDto.getAuthor().getFirstName(), fullBookDto.getAuthor().getLastName());

                book = new Book();
                book.setTitle(fullBookDto.getTitle());
                book.setAuthor(author);

                Set<Category> categories = new HashSet<Category>();
                Category category;

                for (Category bookDtoCategory : fullBookDto.getCategories()) {
                    category = categoryRepository.findByCategoryName(bookDtoCategory.getCategoryName());
                    if (category == null) {
                        category = new Category();
                        category.setCategoryName(bookDtoCategory.getCategoryName());
                    }
                    category.getBooksBelonging().add(book);
                    categories.add(category);
                }

                book.getCategories().addAll(categories);
                savedBooks.add(book);
                bookRepository.save(book);

            }
        }
        return new ResponseEntity(savedBooks, HttpStatus.CREATED) ;
    }

    @Override
    public ResponseEntity deleteBook(BookAuthorDto deleteBookDto) {

        Book book = null;

        Author author = authorRepository.findByFirstNameAndLastName(deleteBookDto.getAuthor().getFirstName(),
                deleteBookDto.getAuthor().getLastName());
        if(author != null) {
            book = bookRepository.findByTitleAndAuthor_id(deleteBookDto.getTitle(), author.getId());

            if (book != null) {
                bookRepository.deleteById(book.getId());
                return new ResponseEntity(book.getTitle() + " deleted.", HttpStatus.OK);
            }
        }
        return new ResponseEntity("Book not found", HttpStatus.OK);
    }

}
