package com.lseraponte.library.service;

import com.lseraponte.library.domain.dao.AuthorRepository;
import com.lseraponte.library.domain.dao.BookRepository;
import com.lseraponte.library.domain.dao.CategoryRepository;
import com.lseraponte.library.domain.model.Author;
import com.lseraponte.library.domain.model.Book;
import com.lseraponte.library.domain.model.Category;
import com.lseraponte.library.web.dto.AddBookDto;
import com.lseraponte.library.web.dto.BookAuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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
    public Book addBook(AddBookDto bookDto) {

        Book book = null;

        Author author = authorRepository.findByFirstNameAndLastName(bookDto.getAuthor().getFirstName(),
                bookDto.getAuthor().getLastName());
        if(author != null)
            book = bookRepository.findByTitleAndAuthor_id(bookDto.getTitle(), author.getId());

        if(book != null)
            return book;

        else {

            if(author == null)
                author = new Author(bookDto.getAuthor().getFirstName(), bookDto.getAuthor().getLastName());

            book = new Book();
            book.setTitle(bookDto.getTitle());
            book.setAuthor(author);

            Set<Category> categories = new HashSet<Category>();
            Category category;

            for (Category bookDtoCategory : bookDto.getCategories()) {
                category = categoryRepository.findByCategoryName(bookDtoCategory.getCategoryName());
                if (category == null) {
                    category = new Category();
                    category.setCategoryName(bookDtoCategory.getCategoryName());
                }
                category.getBooksBelonging().add(book);
                categories.add(category);
            }

            book.getCategories().addAll(categories);
            return bookRepository.save(book);
        }
    }

    @Override
    public String deleteBook(BookAuthorDto deleteBookDto) {

        Book book = null;

        Author author = authorRepository.findByFirstNameAndLastName(deleteBookDto.getAuthor().getFirstName(),
                deleteBookDto.getAuthor().getLastName());
        if(author != null) {
            book = bookRepository.findByTitleAndAuthor_id(deleteBookDto.getTitle(), author.getId());

            if (book != null) {
                bookRepository.deleteById(book.getId());
                return book.getTitle() + " deleted.";
            }
        }
        return "Book not found";
    }

}
