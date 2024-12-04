package packages.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import packages.model.Book;
import packages.service.BookService;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class Controllers {

    private final BookService bookService;

    @GetMapping("/home")
    public ModelAndView welcomePage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("homepage");
        return model;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAll();

        return new ResponseEntity<>(books, HttpStatus.FOUND);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) throws SQLException {
        Book book = bookService.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) throws SQLException {
        Book book = bookService.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        if (!bookDetails.getTitle().isEmpty())
            book.setTitle(bookDetails.getTitle());

        else if(!bookDetails.getAuthor().isEmpty())
            book.setAuthor(bookDetails.getAuthor());

        else if((Integer) bookDetails.getCopies() != null )
            book.setCopies(bookDetails.getCopies());
        bookService.save(book);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws SQLException {
        Book book = bookService.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
