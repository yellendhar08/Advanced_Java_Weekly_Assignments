package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.Exception.BookNotFoundException;
import com.example.LibraryManagementSystem.Model.Book;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    List<Book> books = new ArrayList<>();
    private Long idCounter=1L;

    public void addBook(Book book){
        book.setId(idCounter++);
        books.add(book);
    }
    public List<Book> getAllBooks(){
        return books;
    }
    public Book getBookById(Long id) throws BookNotFoundException {
        return books.stream().filter(b->b.getId().equals(id)).findFirst().orElseThrow(()->new BookNotFoundException("Book not found"));
    }

    public void deleteBookById(Long id) throws BookNotFoundException {
        Book book = getBookById(id);   // this throws exception if not found
        books.remove(book);    }
}
