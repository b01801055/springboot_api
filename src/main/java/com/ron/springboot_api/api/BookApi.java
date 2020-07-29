package com.ron.springboot_api.api;

import com.ron.springboot_api.domain.Book;
import com.ron.springboot_api.dto.BookDTO;
import com.ron.springboot_api.exception.InvalidRequestException;
import com.ron.springboot_api.exception.NotFoundException;
import com.ron.springboot_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<?> listAllBooks() {
        List<Book> books = bookService.findAllBooks();
        if (books.isEmpty()) {
            throw new NotFoundException("Books Not Found");
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new NotFoundException(String.format("book by id %s not found",id));
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> saveBook(@Validated @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        Book book1 = bookService.saveBook(bookDTO.convertToBook());
        return new ResponseEntity<>(book1, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,@Validated @RequestBody BookDTO bookDTO,BindingResult bindingResult) {

        Book currentBook = bookService.getBookById(id);
        if (currentBook == null) {
            throw new NotFoundException(String.format("book by id %s not found",id));
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter",bindingResult);
        }
        //        BeanUtils.copyProperties(bookDTO, currentBook);
        bookDTO.convertToBook(currentBook);
        Book book1 = bookService.updateBook(currentBook);
        return new ResponseEntity<>(book1, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/books")
    public ResponseEntity<?> deleteAllBook() {
        bookService.deleteAllBooks();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
