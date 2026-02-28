package com.example.LibraryManagementSystem.Controller;


import com.example.LibraryManagementSystem.Exception.BookNotFoundException;
import com.example.LibraryManagementSystem.Model.Book;
import com.example.LibraryManagementSystem.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/add-book")
    public String showAddBookForm(Model model){
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result){
        if(result.hasErrors()){
            return "add-book";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "view-books";
    }

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id, Model model) throws BookNotFoundException {
        model.addAttribute("book", bookService.getBookById(id));
        return "book-details";
    }
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }
}
